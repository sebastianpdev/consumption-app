package com.jspapps.consumptionapp.domain.usecase;

import com.google.common.collect.Lists;
import com.jspapps.consumptionapp.application.config.AppConfig;
import com.jspapps.consumptionapp.application.exception.CustomRuntimeException;
import com.jspapps.consumptionapp.application.util.annotation.UseCase;
import com.jspapps.consumptionapp.application.util.constant.AppConstant;
import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.port.out.ICreateConsumptionDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.jspapps.consumptionapp.application.util.constant.AppConstant.CSV_DATE_FORMAT;

@UseCase
public class UploadCsvFileUseCase {

    private final ThreadPoolTaskExecutor taskExecutor;
    private final ICreateConsumptionDAO createConsumptionUseCase;

    public UploadCsvFileUseCase(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor, ICreateConsumptionDAO createConsumptionUseCase) {
        this.taskExecutor = taskExecutor;
        this.createConsumptionUseCase = createConsumptionUseCase;
    }

    public void processFile(Resource file) {
        try {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .parse(new BufferedReader(new InputStreamReader(file.getInputStream())));
            List<CSVRecord> records = csvParser.getRecords();

            int batchSize = (int) Math.ceil((double) records.size() / AppConfig.THREADS);

            List<Future<List<ConsumptionDTO>>> tasks = new ArrayList<>();

            // Apoyado del numero de hilos disponibles se calcula la cantidad de registros por lote
            for (int i = 0; i < AppConfig.THREADS; i++) {
                int fromIndex = i * batchSize;
                int toIndex = Math.min((i+1) * batchSize, records.size());

                List<CSVRecord> batch = records.subList(fromIndex, toIndex);

                ProcessConsumptionRecord consumptionRecord = new ProcessConsumptionRecord(batch);

                // Se ejecuta en paralelo un lote de registros
                Future<List<ConsumptionDTO>> task = taskExecutor.submit(consumptionRecord);

                // Acumulamos las tareas que envian a procesar
                tasks.add(task);
            }

            // Se acumulan los registros procesado del csv por cada task ejecutada
            List<ConsumptionDTO> consumptionList = new ArrayList<>();
            for (Future<List<ConsumptionDTO>> task: tasks) {
                List<ConsumptionDTO> recordConsumptionDone = task.get();
                consumptionList.addAll(recordConsumptionDone);
            }

            csvParser.close();

            saveConsumptionRecord(consumptionList);

            taskExecutor.shutdown();

        } catch (IOException | ExecutionException | InterruptedException ex) {
            throw new CustomRuntimeException(AppConstant.ERROR_UPLOAD_CSV_FILE_, AppConstant.ERROR_UPLOAD_CSV_FILE, ex);
        }
    }

    /**
     * Guarda por partes la lista de registros procesados del csv
     * @param consumptionList lista de registros procesados del csv
     */
    private void saveConsumptionRecord(List<ConsumptionDTO> consumptionList) {
        for (List<ConsumptionDTO> mConsumption: Lists.partition(consumptionList, AppConstant.BATCH_SAVING_RECORDS)) {
            createConsumptionUseCase.saveConsumption(mConsumption);
        }
    }

    /**
     * Clase encargada de procesar un lote de registros del archivo csv y genera una lista de DTO's
     * necesarios para guardar en la base de datos.
     */
    public static class ProcessConsumptionRecord implements Callable<List<ConsumptionDTO>> {
        private List<CSVRecord> energyRecords;

        public ProcessConsumptionRecord(List<CSVRecord> batch) {
            this.energyRecords = batch;
        }

        @Override
        public List<ConsumptionDTO> call() throws Exception {
            List<ConsumptionDTO> recordProcessed = new ArrayList<>();

            for (CSVRecord mRecord: energyRecords) {
                recordProcessed.add(parseRecord(mRecord));
            }

            return recordProcessed;
        }

        private ConsumptionDTO parseRecord(CSVRecord csvRecord) {
            var id = String.valueOf(csvRecord.get("id"));
            var meterId = Integer.parseInt(csvRecord.get("meter_id"));
            var activeEnergy = new BigDecimal(csvRecord.get("active_energy"));
            var reactiveEnergy = new BigDecimal(csvRecord.get("reactive_energy"));
            var capacitiveReactive = new BigDecimal(csvRecord.get("capacitive_reactive"));
            var solar = new BigDecimal(csvRecord.get("solar"));
            var date = parseDate(String.valueOf(csvRecord.get("date")));

            return ConsumptionDTO.builder()
                    .id(id).meter(meterId).activeEnergy(activeEnergy).reactiveEnergy(reactiveEnergy).capacitiveReactive(capacitiveReactive)
                    .solar(solar).date(date)
                    .build();
        }

        private Instant parseDate(String date) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CSV_DATE_FORMAT);
            OffsetDateTime dateTime = OffsetDateTime.parse(date, formatter);
            return dateTime.toInstant();
        }
    }
}
