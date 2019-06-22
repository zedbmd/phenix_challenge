package com.phenix.model;

public class DataFactory {

	public static Data getData(final String dataType, final String fileName, final String line, final String delimiter) {
		if("product".equalsIgnoreCase(dataType)) return new Product(line, fileName, delimiter);
		else if("transaction".equalsIgnoreCase(dataType)) return new Transaction(line, delimiter);

		return null;
	}

}
