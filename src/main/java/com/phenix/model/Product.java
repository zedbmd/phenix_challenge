package com.phenix.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Product implements Data{
	final static String LOCAL_DATE_FORMAT = "yyyyMMdd";

	public String store;
	public Date date;
	public int product;
	public float price;

	/**
	 * 
	 * @param store : UUID id of the store
	 * @param product : id of the product
	 * @param price ; price of the product in the store in Euros
	 */
	public Product(String store, Date date, int product, float price) {
		this.store = store;
		this.date = date;
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
		this.date = getDate(fileName);
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

	/**
	 * Get store id from file name
	 * @param fileName
	 * @return
	 */
	public String getStore(String fileName) {
		return fileName.substring(15, fileName.length() - 14);
	}

	/**
	 * Gets date from file name
	 * @param fileName
	 * @return
	 */
	public Date getDate(String fileName) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);
		String dateText = fileName.substring(fileName.length() - 13, fileName.length() - 5);

		return Date.valueOf(LocalDate.parse(dateText,formatter));
	}

	/**
	 * 
	 */
	public String toString() {
		return "\nstore ID : " + this.store + ", " 
				+ "product : " + this.product + ", "
				+ "date : " + this.date + ", "
				+ "price : " + this.price;
	}
}
