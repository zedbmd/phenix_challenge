package com.phenix.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Transaction implements Data {
	final static String LOCAL_TIME_FORMAT = "yyyyMMdd'T'HHmmssZ";
	final static String DELIMITER = "|";
	final static int Max_QUANTITY = 100;

	public long id;
	public Timestamp timestamp;
	public LocalTime time;
	public Date date;
	public String store;
	public int product;
	public int quantity;

	public Transaction(Date date, String store) {
		this.id = getRandomId();
		this.timestamp = getRandomTimestamp(date);
		this.store = store;
		this.date = date;
		//this.time = this.timestamp.toLocalDateTime();
		this.product = getRandomProduct();
		this.quantity = getRandomQuantity();
	}

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

	private int getRandomId(){
		Random random = new Random();

		return Math.abs(random.nextInt(10000));
	}

	private Timestamp getRandomTimestamp(Date date){
		Random r = new Random(); 

		return new Timestamp(date.getTime() + r.nextInt(1000));
	}

	private int getRandomQuantity(){
		Random r = new Random();

		return Math.abs(r.nextInt(Max_QUANTITY));
	}

	public String getLine(){
		DateFormat simple = new SimpleDateFormat(LOCAL_TIME_FORMAT);

		return this.id + DELIMITER
				+ simple.format(new Date(this.timestamp.getTime())) + DELIMITER
				+ this.store + DELIMITER
				+ this.product  + DELIMITER 
				+ this.quantity + "\r\n";
	}

	/**
	 * 
	 * @param date
	 * @param store : not needed for this implementation
	 * @return
	 */
	public static String getFileName(Date date){		
		return "transactions_" + date.toLocalDate().format(DateTimeFormatter.BASIC_ISO_DATE) + ".data";
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
