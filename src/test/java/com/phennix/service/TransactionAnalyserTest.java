package com.phennix.service;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.phenix.model.Transaction;
import com.phenix.service.DataManager;
import com.phenix.service.TransactionAnalyser;

public class TransactionAnalyserTest {
	
	DataManager dataManager;;
	Date dayOfExtract;
	Map<String, List<Transaction>> lastDayStoresTransactions;
	Map<String, List<Transaction>> allStoresTransactions;

	@Before
	public void init() {
		dataManager = new DataManager("src/test/resources/data/products", 
				"src/test/resources/data/transactions", 
				"src/test/output", 
				"\\|");
		dayOfExtract = Date.valueOf("2017-05-14");	
		List<Transaction> transactionFiles = dataManager.loadTransactionFiles(dayOfExtract, 7);
		
		//System.out.println(transactionFiles);

		lastDayStoresTransactions = transactionFiles.stream().filter(transaction -> transaction.date.equals(dayOfExtract))
				.collect(Collectors.groupingBy(transaction -> transaction.store));
		allStoresTransactions = transactionFiles.stream().collect(Collectors.groupingBy(transaction -> transaction.store));
	}
	
	
	@Test
	public void testSearchTopProductSold() {
		TransactionAnalyser.searchTopProductSold(lastDayStoresTransactions, dataManager, dayOfExtract);
		File outputFileStore1 = new File("src/test/output/top_100_ventes_2a4b6b81-5aa2-4ad8-8ba9-ae1a006e7d71_20170514.data");
		File outputResultStore1 = new File("src/test/output/result/top_100_ventes_2a4b6b81-5aa2-4ad8-8ba9-ae1a006e7d71_20170514.data");

		File outputFileStore2 = new File("src/test/output/top_100_ventes_bdc2a431-797d-4b07-9567-67c565a67b84_20170514.data");
		File outputResultStore2 = new File("src/test/output/result/top_100_ventes_bdc2a431-797d-4b07-9567-67c565a67b84_20170514.data");
		try {
		assertTrue(Arrays.equals(Files.readAllBytes(outputFileStore1.toPath()), Files.readAllBytes(outputResultStore1.toPath()))
				&& Arrays.equals(Files.readAllBytes(outputFileStore2.toPath()), Files.readAllBytes(outputResultStore2.toPath())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchTopProductTurnOver(){
		TransactionAnalyser.searchTopProductTurnOver(allStoresTransactions, dataManager, dayOfExtract, 7);
		File outputFileStore1 = new File("src/test/output/top_100_ca_2a4b6b81-5aa2-4ad8-8ba9-ae1a006e7d71_20170514.data");
		File outputResultStore1 = new File("src/test/output/result/top_100_ca_2a4b6b81-5aa2-4ad8-8ba9-ae1a006e7d71_20170514.data");

		File outputFileStore2 = new File("src/test/output/top_100_ca_bdc2a431-797d-4b07-9567-67c565a67b84_20170514.data");
		File outputResultStore2 = new File("src/test/output/result/top_100_ca_bdc2a431-797d-4b07-9567-67c565a67b84_20170514.data");
		try {
		assertTrue(Arrays.equals(Files.readAllBytes(outputFileStore1.toPath()), Files.readAllBytes(outputResultStore1.toPath()))
				&& Arrays.equals(Files.readAllBytes(outputFileStore2.toPath()), Files.readAllBytes(outputResultStore2.toPath())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}