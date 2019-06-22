package com.phenix.model;

import java.util.InputMismatchException;

public interface Data {

	/**
	 * 
	 * @param line
	 * @param delimiter
	 * @return
	 * @throws InputMismatchException
	 */
	public void getRecordFromLine(String line, String delimiter) throws InputMismatchException;

}
