package com.jspapps.consumptionapp.infrastructure.util.constant;

public class AppConstant {

    // CSV File to load
    public static final String CSV_FILE_PATH = "test_bia.csv";
    public static final String CSV_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssX";

    // Batch size to save records
    public static final int BATCH_SAVING_RECORDS = 500;

    // Error message
    public static final String ERROR_UPLOAD_CSV_FILE_ = "Error durante el procesamiento del archivo csv.";
    public static final String ERROR_UPLOAD_CSV_FILE = "ERROR_UPLOAD_CSV_FILE";
    public static final String ERROR_LIST_CONSUMPTION_RECORDS_ = "Error durante busqueda de consumos.";
    public static final String ERROR_LIST_CONSUMPTION_RECORDS = "ERROR_LIST_CONSUMPTION_RECORDS";

}
