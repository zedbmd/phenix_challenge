package com.phenix.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaction implements Data {
	final static String LOCAL_TIME_FORMAT = "yyyyMMdd'T'HHmmssZ";

	public long id;
	public LocalTime time;
	public Date date;
	public String store;
	public int product;
	public int quantity;

	/**
	 * 
	 * @param id : id of the transaction
	 * @param dateTime : date & time in ISO 8601 format
	 * @param store : UUID id of the store
	 * @param product : id of the product
	 * @param quantity : quantity sold of the product
	 */
	public Transaction(long id, Date date, LocalTime time, String store, int product, int quantity) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.store = store;
		this.product = product;
		this.quantity = quantity;
	}

	/**
	 * 
	 * @param line
	 * @param delimiter
	 */
	public Transaction(String line, String delimiter) {
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
			this.id = rowScanner.nextLong();
			LocalDateTime dateTime =  LocalDateTime.parse(rowScanner.next(), DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT));
			this.date = Date.valueOf(dateTime.toLocalDate());
			this.time = dateTime.toLocalTime();
			this.store = rowScanner.next();
			this.product = rowScanner.nextInt();
			this.quantity = rowScanner.nextInt();
		} catch(InputMismatchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public String toString() {
		return "Transaction ID : " + this.id + ", " 
				+ "Day : " + this.date + ", "
				+ "Time : " + this.time + ", "
				+ "store : " + this.store + ", " 
				+ "product : " + this.product  + ", "
				+ "quantity : " + this.quantity + "\n";
	}
}
