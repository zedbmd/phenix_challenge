package com.phenix;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.phenix.model.Transaction;

import com.phenix.service.DataManager;
import com.phenix.service.DataGenerator;
import com.phenix.service.TransactionAnalyser;

import com.phenix.model.Data;

public class Main {
	final private static String DELIMITER = "\\|";

	public static void main(String[] args) throws IOException {
		Date dayOfExtract = Date.valueOf(args[0]); // 2017-05-14
		int maxDays = Integer.valueOf(args[1]); // 1000;
		int maxStores = Integer.valueOf(args[2]); // 1000;
		int maxValues = Integer.valueOf(args[3]); // 1000;
		String outputFilePath = args[4]; // "src/main/output/"
		String dataGenerationFolder = args[5]; // "src/main/output/data_generated/"

		String productFolderPath = dataGenerationFolder + "/product/"; 
		String transactionFolderPath = dataGenerationFolder + "/transaction/"; 

		for(int day = 0; day < maxDays; day++) {
			Date date = Date.valueOf(dayOfExtract.toLocalDate().minusDays(day));

			for(int storeCounter = 0; storeCounter < maxStores; storeCounter++) {
				String store = Data.getRandomStore();
				DataGenerator transactions = new DataGenerator("transaction", transactionFolderPath, date, store, maxValues);
				transactions.generate();
				DataGenerator products = new DataGenerator("product", productFolderPath, date, store, maxValues);
				products.generate();
			}
		}

		DataManager dataManager = new DataManager(productFolderPath, transactionFolderPath, outputFilePath, DELIMITER);
		List<Transaction> transactionFiles = dataManager.loadTransactionFiles(dayOfExtract, maxDays);

		Map<String, List<Transaction>> lastDayStoresTransactions = transactionFiles.stream().filter(transaction -> transaction.date.equals(dayOfExtract))
				.collect(Collectors.groupingBy(transaction -> transaction.store));
		Map<String, List<Transaction>> allStoresTransactions = transactionFiles.stream().collect(Collectors.groupingBy(transaction -> transaction.store));

		TransactionAnalyser.searchTopProductSold(lastDayStoresTransactions, dataManager, dayOfExtract);
		TransactionAnalyser.searchTopProductTurnOver(allStoresTransactions, dataManager, dayOfExtract, maxDays);
	}
}
