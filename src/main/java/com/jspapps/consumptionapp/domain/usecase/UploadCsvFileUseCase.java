package com.jspapps.consumptionapp.domain.usecase;

import com.google.common.collect.Lists;
import com.jspapps.consumptionapp.application.config.AppConfig;
import com.jspapps.consumptionapp.application.util.annotation.UseCase;
import com.jspapps.consumptionapp.application.util.constant.AppConstant;
import com.jspapps.consumptionapp.domain.dto.SaveConsumption;
import com.jspapps.consumptionapp.domain.port.out.ICreateConsumptionUseCase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@UseCase
public class UploadCsvFileUseCase {

    private final ThreadPoolTaskExecutor taskExecutor;
    private final ICreateConsumptionUseCase createConsumptionUseCase;

    public static final String CSV_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssX";

    public UploadCsvFileUseCase(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor, ICreateConsumptionUseCase createConsumptionUseCase) {
        this.taskExecutor = taskExecutor;
        this.createConsumptionUseCase = createConsumptionUseCase;
    }

    public void processFile(String filePath) throws IOException, ExecutionException, InterruptedException {
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(filePath));
        List<CSVRecord> records = csvParser.getRecords();

        int batchSize = (int) Math.ceil((double) records.size() / AppConfig.THREADS);

        List<Future<List<SaveConsumption>>> tasks = new ArrayList<>();

        for (int i = 0; i < AppConfig.THREADS; i++) {
            int fromIndex = i * batchSize;
            int toIndex = Math.min((i+1) * batchSize, records.size());

            List<CSVRecord> batch = records.subList(fromIndex, toIndex);

            ProcessConsumptionRecord consumptionRecord = new ProcessConsumptionRecord(batch);

            Future<List<SaveConsumption>> task = taskExecutor.submit(consumptionRecord);
            tasks.add(task);
        }

        List<SaveConsumption> consumptionList = new ArrayList<>();
        for (Future<List<SaveConsumption>> task: tasks) {
            List<SaveConsumption> recordConsumptionDone = task.get();
            consumptionList.addAll(recordConsumptionDone);
        }

        csvParser.close();

        saveConsumptionRecord(consumptionList);

        taskExecutor.shutdown();
    }

    private void saveConsumptionRecord(List<SaveConsumption> consumptionList) {
        for (List<SaveConsumption> mConsumption: Lists.partition(consumptionList, AppConstant.BATCH_SAVING_RECORDS)) {
            createConsumptionUseCase.saveConsumption(mConsumption);
        }
    }

    private static class ProcessConsumptionRecord implements Callable<List<SaveConsumption>> {
        private List<CSVRecord> energyRecords;

        public ProcessConsumptionRecord(List<CSVRecord> batch) {
            this.energyRecords = batch;
        }

        @Override
        public List<SaveConsumption> call() throws Exception {
            List<SaveConsumption> recordProcessed = new ArrayList<>();

            for (CSVRecord mRecord: energyRecords) {
                recordProcessed.add(parseRecord(mRecord));
            }

            return recordProcessed;
        }

        private SaveConsumption parseRecord(CSVRecord csvRecord) {
            var id = String.valueOf(csvRecord.get("id"));
            var meterId = Integer.parseInt(csvRecord.get("meter_id"));
            var activeEnergy = new BigDecimal(csvRecord.get("active_energy"));
            var reactiveEnergy = new BigDecimal(csvRecord.get("reactive_energy"));
            var capacitiveReactive = new BigDecimal(csvRecord.get("capacitive_reactive"));
            var solar = new BigDecimal(csvRecord.get("solar"));
            var date = stringToInstant(String.valueOf(csvRecord.get("date")));

            return SaveConsumption.builder()
                    .id(id).meter(meterId).activeEnergy(activeEnergy).reactiveEnergy(reactiveEnergy).capacitiveReactive(capacitiveReactive)
                    .solar(solar).date(date)
                    .build();
        }

        private Instant stringToInstant(String date) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CSV_DATE_FORMAT);
            OffsetDateTime dateTime = OffsetDateTime.parse(date, formatter);
            return dateTime.toInstant();
        }
    }
}
