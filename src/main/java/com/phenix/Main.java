package com.phenix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import com.phenix.model.Transaction;

import com.phenix.service.DataManager;
import com.phenix.service.DataGenerator;
import com.phenix.service.TransactionAnalyser;

import com.phenix.model.Data;

public class Main {
	final private static String DELIMITER = "\\|";
	public static Date dayOfExtract;
	public static int maxDays;
	public static int maxStores;
	public static int maxValues;
	public static String outputFilePath;
	public static String dataGenerationFolder;
	public static String productFolderPath; 
	public static String transactionFolderPath;

	public static void main(String[] args) throws IOException {

		loadProperties(args);

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

	public static void loadProperties(String[] args) {

		Properties prop = new Properties();
		String fileName = args[0];
		InputStream is = null;

		try {
			is = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			System.out.println(fileName);
			e.printStackTrace();
		}
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		dayOfExtract = Date.valueOf(prop.getProperty("day.of.extract"));
		maxDays = Integer.valueOf(prop.getProperty("max.days"));
		maxStores = Integer.valueOf(prop.getProperty("max.stores"));
		maxValues = Integer.valueOf(prop.getProperty("max.values"));
		outputFilePath = prop.getProperty("output.file.path");
		dataGenerationFolder = prop.getProperty("data.generation.folder)");

		productFolderPath = dataGenerationFolder + "/product/"; 
		transactionFolderPath = dataGenerationFolder + "/transaction/"; 
	}
}
