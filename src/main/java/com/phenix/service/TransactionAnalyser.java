package com.phenix.service;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.phenix.model.Transaction;

public final class TransactionAnalyser {

	public static void searchTopProductSold(Map<String, List<Transaction>> storeTransactions, String oututFilePath, Date date) {
		HashMap<Integer, Integer> productQuantity = new HashMap<>();
		
		storeTransactions.forEach((String store, List<Transaction> transactionList) -> {
			transactionList.stream().filter(transaction -> transaction.quantity != 0).collect(Collectors.groupingBy(transaction -> transaction.product))
			.forEach((Integer product, List<Transaction> transactions) -> {
				productQuantity.put(product, transactions.stream().mapToInt(transaction -> transaction.quantity).sum());
			});
			try {
				DataManager.saveTopProdcut(productQuantity, store, date, oututFilePath, 100);
			} catch (FileNotFoundException e) {
				System.out.println("File not found" + oututFilePath);
				e.printStackTrace();
			}
		});		
	}

	public static void searchTopProductTurnOver(Map<String, List<Transaction>> storeTransactions) {
		System.out.println(storeTransactions);
	}
}
