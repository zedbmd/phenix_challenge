package com.phenix.model;

import java.security.Timestamp;

public class Transaction {
	int id;
	Timestamp dateTime;
	String store;
	int product;
	int quantity;

	/**
	 * 
	 * @param id : id of the transaction
	 * @param dateTime : date & time in ISO 8601 format
	 * @param store : UUID id of the store
	 * @param product : id of the product
	 * @param quantity : quantity sold of the product
	 */
	public Transaction(int id, Timestamp dateTime, String store, int product, int quantity) {
		this.id = id;
		this.dateTime = dateTime;
		this.store = store;
		this.product = product;
		this.quantity = quantity;
	}
}
