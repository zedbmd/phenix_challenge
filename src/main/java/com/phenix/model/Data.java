package com.phenix.model;

import java.util.InputMismatchException;
import java.util.Random;

public interface Data {

	/**
	 * 
	 * @param line
	 * @param delimiter
	 * @return
	 * @throws InputMismatchException
	 */
	public void getRecordFromLine(String line, String delimiter) throws InputMismatchException;

	/**
	 * get a line of data  
	 * @param delimiter
	 */
	public String getLine();

	public static String getRandomStore(){
		String store; 
		store = getRandomString(8) + "-" + getRandomString(4) + "-" + getRandomString(4) + "-" + getRandomString(4) + "-" + getRandomString(12);

		return store;
	}

	public static String getRandomString(int length) {
	    String lower = "abcdefghijklmnopqrstuvwxyz";
	    String digits = "0123456789";
	    String alphanum = lower + digits;

		char[] buf = new char[length];
	    Random random = new Random();
	    char[] symbols = alphanum.toCharArray();

		for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];

        return new String(buf);
	}

	public default int getRandomProduct(){
		Random random = new Random();

		return Math.abs(random.nextInt(100));
	}
}
