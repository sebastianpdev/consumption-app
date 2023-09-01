package com.jspapps.consumptionapp;

import com.jspapps.consumptionapp.infrastructure.util.constant.AppConstant;
import com.jspapps.consumptionapp.domain.usecase.UploadCsvFileUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ConsumptionappApplication implements CommandLineRunner {

	private final UploadCsvFileUseCase uploadCsvFileUseCase;

	public ConsumptionappApplication(UploadCsvFileUseCase uploadCsvFileUseCase) {
		this.uploadCsvFileUseCase = uploadCsvFileUseCase;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumptionappApplication.class, args);
	}

	@Override
	public void run(String... args) {
		var csvFile = new ClassPathResource(AppConstant.CSV_FILE_PATH);
		uploadCsvFileUseCase.processFile(csvFile);
	}
}
