package com.phenix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.phenix.model.Data;
import com.phenix.model.DataLoader;

public class Main {
	final private static String DELIMITER = "\\|";
	
	public static void main(String[] args) throws IOException {
		String productFilePath = "C:/Users/pc/Desktop/workspace/project/src/main/resources/data/products";
		String transactionFilePath = "C:/Users/pc/Desktop/workspace/project/src/main/resources/data/transactions";
		List<Data> productFiles = new ArrayList<>();
		List<Data> transactionFiles = new ArrayList<>();

		DataLoader.loadFiles("product", productFilePath, DELIMITER, productFiles);
		DataLoader.loadFiles("transaction", transactionFilePath, DELIMITER, transactionFiles);
	}

}