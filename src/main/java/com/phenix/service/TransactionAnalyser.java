package com.phenix.service;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.phenix.model.Product;
import com.phenix.model.Transaction;
import com.phenix.service.DataManager;

public final class TransactionAnalyser {

	/**
	 * Search top 100 product per store in the last day, and store the result in a file
	 * @param storeTransactions
	 * @param dataManager
	 * @param date
	 */
	public static void searchTopProductSold(Map<String, List<Transaction>> storeTransactions, DataManager dataManager, Date date) {
		HashMap<Integer, Integer> productQuantity = new HashMap<>();
		
		storeTransactions.forEach((String store, List<Transaction> transactionList) -> {
			transactionList.stream().filter(transaction -> transaction.quantity != 0).collect(Collectors.groupingBy(transaction -> transaction.product))
			.forEach((Integer product, List<Transaction> transactions) -> {
				productQuantity.put(product, transactions.stream().mapToInt(transaction -> transaction.quantity).sum());
			});
			try {
				System.out.println(productQuantity);
				dataManager.saveTopProdcut(productQuantity, store, date, 100);
			} catch (FileNotFoundException e) {
				System.out.println("File not found" + dataManager.getOutputPath());
				e.printStackTrace();
			}
		});		
	}

	/**
	 * Search top 100 product turnover per store in the last week, and store the result in a file
	 * @param storeTransactions
	 * @param dataManager
	 * @param date
	 * @param numberOfDays
	 */
	public static void searchTopProductTurnOver(Map<String, List<Transaction>> storeTransactions, DataManager dataManager, Date date, int numberOfDays) {
		HashMap<Integer, Double> productTurnOver = new HashMap<>();
		List<Product> productFiles = dataManager.loadProductFiles(date, numberOfDays);

		storeTransactions.forEach((String store, List<Transaction> transactionList) -> {

			transactionList.stream().filter(transaction -> transaction.quantity != 0).collect(Collectors.groupingBy(transaction -> transaction.product))
			.forEach((Integer product, List<Transaction> transactions) -> {
			//	Double price = productFiles.get
			//	productTurnOver.put(product, transactions.stream().mapToInt(transaction -> transaction.quantity).sum());
			});
			try {
				dataManager.saveTopProdcutTurnOver(productTurnOver, store, date, 100);
			} catch (FileNotFoundException e) {
				System.out.println("File not found" + dataManager.getOutputPath());
				e.printStackTrace();
			}
		});
	}
}
