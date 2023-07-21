package com.jspapps.consumptionapp.domain.usecase;

import com.jspapps.consumptionapp.application.config.AppConfigTextContextConfiguration;
import com.jspapps.consumptionapp.application.util.DateUtils;
import com.jspapps.consumptionapp.application.util.constant.AppConstant;
import com.jspapps.consumptionapp.domain.dto.ConsumptionDTO;
import com.jspapps.consumptionapp.domain.port.out.ICreateConsumptionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = AppConfigTextContextConfiguration.class)
public class UploadCsvFileUseCaseTest {

    private UploadCsvFileUseCase uploadCsvFileUseCaseUnderTest;

    @Mock private ICreateConsumptionDAO createConsumptionUseCase;

    /*@Autowired
    @Qualifier("taskExecutor")*/
    @Mock
    private ThreadPoolTaskExecutor taskExecutor;

    private ConsumptionDTO consumptionDTO, consumptionDTOTwo;
    private static final String CONSUMPTION_ID_ONE = "1bcc9369";
    private static final String CONSUMPTION_ID_TWO = "1ass4412";
    private static final String DATE_VALUE = "2023-06-01";

    @BeforeEach
    public void init() {
        uploadCsvFileUseCaseUnderTest = new UploadCsvFileUseCase(taskExecutor, createConsumptionUseCase);

        consumptionDTO = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_ONE).meter(1).activeEnergy(new BigDecimal("0.00")).reactiveEnergy(new BigDecimal("0.00"))
                .capacitiveReactive(new BigDecimal("0.00")).solar(new BigDecimal("0.00")).date(convertStringToInstant())
                .build();

        consumptionDTOTwo = ConsumptionDTO.builder()
                .id(CONSUMPTION_ID_TWO).meter(2).activeEnergy(new BigDecimal("0.00")).reactiveEnergy(new BigDecimal("0.00"))
                .capacitiveReactive(new BigDecimal("0.00")).solar(new BigDecimal("0.00")).date(convertStringToInstant())
                .build();
    }

    @Disabled("Problema al intentar simular concurrencia")
    @Test
    void givenCsvFilePath_whenProcessFile_thenSaveConsumptionRecords() {

        var csvFile = new ClassPathResource(AppConstant.CSV_FILE_PATH);

        List<ConsumptionDTO> consumptionDTOList = getConsumptionDTOList();

        UploadCsvFileUseCase.ProcessConsumptionRecord processConsumptionRecordMock = mock(UploadCsvFileUseCase.ProcessConsumptionRecord.class);

        // Simulamos el comportamiento de sumbit
        CompletableFuture<List<ConsumptionDTO>> futureMock = CompletableFuture.completedFuture(consumptionDTOList);
        //when(taskExecutor.submit(processConsumptionRecordMock)).thenReturn(futureMock);
        doReturn(futureMock).when(taskExecutor).submit(processConsumptionRecordMock);

        //when(futureMock.get()).thenReturn(consumptionDTOList);

        uploadCsvFileUseCaseUnderTest.processFile(csvFile);

    }

    private Instant convertStringToInstant() {
        return DateUtils.convertStringToInstant(DATE_VALUE);
    }

    private List<ConsumptionDTO> getConsumptionDTOList() {
        return List.of(consumptionDTO, consumptionDTOTwo);
    }
}
