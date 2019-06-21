package com.phenix.model;

public class Product {
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
}
