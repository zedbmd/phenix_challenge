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
		Date dayOfExtract = Date.valueOf(args[0]); // 2017-05-14
		int numberOfDays = Integer.valueOf(args[1]); // 7
		String productFolderPath = args[2]; // "C:/Users/pc/Desktop/workspace/project/src/main/resources/data/products" 
		String transactionFolderPath = args[3]; // "C:/Users/pc/Desktop/workspace/project/src/main/resources/data/transactions" 
		String outputFilePath = args[4]; // "C:/Users/pc/Desktop/workspace/project/src/main/output/"

		DataManager dataManager = new DataManager(productFolderPath, transactionFolderPath, outputFilePath, DELIMITER);
		List<Transaction> transactionFiles = dataManager.loadTransactionFiles(dayOfExtract, numberOfDays);

		Map<String, List<Transaction>> lastDayStoresTransactions = transactionFiles.stream().filter(transaction -> transaction.date.equals(dayOfExtract))
				.collect(Collectors.groupingBy(transaction -> transaction.store));
		Map<String, List<Transaction>> allStoresTransactions = transactionFiles.stream().collect(Collectors.groupingBy(transaction -> transaction.store));

		TransactionAnalyser.searchTopProductSold(lastDayStoresTransactions, dataManager, dayOfExtract);
		TransactionAnalyser.searchTopProductTurnOver(allStoresTransactions, dataManager, dayOfExtract, numberOfDays);
	}
}
