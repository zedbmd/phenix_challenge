package com.phenix;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.phenix.model.Data;
import com.phenix.model.Transaction;
import com.phenix.service.DataManager;

public class Main {
	final private static String DELIMITER = "\\|";

	public static void main(String[] args) throws IOException {
		Date dayOfExtract = Date.valueOf(args[0]);
		int numberOfDays = Integer.valueOf(args[1]);
		String productFolderPath = args[2];
		String transactionFolderPath = args[3];
		List<Data> productFiles = new ArrayList<>();

		DataManager.loadFiles("product", productFolderPath, dayOfExtract, numberOfDays, DELIMITER, productFiles);
		List<Transaction> transactionFiles = DataManager.loadTransactionFiles(transactionFolderPath, dayOfExtract, numberOfDays, DELIMITER);
	}

}
