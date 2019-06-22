package com.phenix.model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Product implements Data{
	String store;
	int product;
	float price;

	/**
	 * 
	 * @param store : UUID id of the store
	 * @param product : id of the product
	 * @param price ; price of the product in the store in Euros
	 */
	public Product(String store, int product, float price) {
		this.store = store;
		this.product = product;
		this.price = price;
	}

	/**
	 * 
	 * @param line
	 * @param delimiter
	 */
	public Product(String line, String fileName, String delimiter) {
		this.store = getStore(fileName);
		getRecordFromLine(line, delimiter);
	}

	/**
	 * 
	 * @param line
	 * @param delimiter
	 * @return
	 */
	public void getRecordFromLine(String line, String delimiter) throws InputMismatchException {
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(delimiter);

			this.product = rowScanner.nextInt();
			this.price = Float.parseFloat(rowScanner.next());
		} catch(InputMismatchException e) {
			e.printStackTrace();
			System.out.println(line);
		}
	}

	public String getStore(String fileName) {
		return fileName.substring(15, fileName.length() - 14);
	}

	/**
	 * 
	 */
	public String toString() {
		return "\nstore ID : " + this.store + ", " 
				+ "product : " + this.product + ", "
				+ "price : " + this.price;
	}
}
