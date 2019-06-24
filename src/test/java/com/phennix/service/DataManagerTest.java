package com.phennix.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.phenix.model.Transaction;
import com.phenix.model.Product;
import com.phenix.service.DataManager;

public class DataManagerTest {

	DataManager dataManager;

	@Before
	public void init() {
		dataManager = new DataManager("src/test/resources/data/products", 
				"src/test/resources/data/transactions", 
				"src/test/output", 
				"\\|");
	}

	@Test
	public void testLoadTransactionFiles() {
		List<Transaction> transactions = dataManager.loadTransactionFiles(Date.valueOf("2017-05-14"), 7);
		assertEquals(transactions.size(), 8);
	}

	@Test
	public void testLoadProductFiles() {
		List<Product> transactions = dataManager.loadProductFiles(Date.valueOf("2017-05-14"), 7);
		assertEquals(transactions.size(), 80);
	}
}
