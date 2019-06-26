package com.phenix.model;

import java.sql.Date;

public class DataFactory {

	/**
	 * 
	 * @param dataType
	 * @param fileName
	 * @param line
	 * @param delimiter
	 * @return
	 */
	public static Data getData(final String dataType, final String fileName, final String line, final String delimiter) {
		if("product".equalsIgnoreCase(dataType)) return new Product(line, fileName, delimiter);
		else if("transaction".equalsIgnoreCase(dataType)) return new Transaction(line, delimiter);

		return null;
	}

	/**
	 * 
	 * @param dataType
	 * @return
	 */
	public static Data getData(final String dataType, final Date date, final String store) {
		if("product".equalsIgnoreCase(dataType)) return new Product(date, store);
		else if("transaction".equalsIgnoreCase(dataType)) return new Transaction(date, store);

		return null;
	}

	/**
	 * 
	 * @param dataType
	 * @param date
	 * @param store
	 * @return
	 */
	public static String getFileName(final String dataType, Date date, String store) {
		if("product".equalsIgnoreCase(dataType)) return Product.getFileName(date, store);
		else if("transaction".equalsIgnoreCase(dataType)) return Transaction.getFileName(date);

		return "";
	}
}
