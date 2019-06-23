package com.phenix;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.phenix.model.Transaction;

import com.phenix.service.DataManager;
import com.phenix.service.TransactionAnalyser;

public class Main {
	final private static String DELIMITER = "\\|";

	public static void main(String[] args) throws IOException {
		Date dayOfExtract = Date.valueOf(args[0]);
		int numberOfDays = Integer.valueOf(args[1]);
		String productFolderPath = args[2];
		String transactionFolderPath = args[3];
		String outputFilePath = args[4];

		DataManager dataManager = new DataManager(productFolderPath, transactionFolderPath, DELIMITER);
		List<Transaction> transactionFiles = dataManager.loadTransactionFiles(dayOfExtract, numberOfDays);

		Map<String, List<Transaction>> lastDayStoresTransactions = transactionFiles.stream().filter(transaction -> transaction.date.equals(dayOfExtract))
				.collect(Collectors.groupingBy(transaction -> transaction.store));
		Map<String, List<Transaction>> allStoresTransactions = transactionFiles.stream().collect(Collectors.groupingBy(transaction -> transaction.store));

		TransactionAnalyser.searchTopProductSold(lastDayStoresTransactions, dataManager, dayOfExtract);
		TransactionAnalyser.searchTopProductTurnOver(allStoresTransactions, dataManager, dayOfExtract);
	}
}
