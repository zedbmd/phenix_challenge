package com.phenix.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.phenix.model.Data;
import com.phenix.model.DataFactory;
import com.phenix.model.Transaction;

public final class DataManager {

	/**
	 * 
	 * @param fileType
	 * @param filePath
	 * @param delimiter
	 * @return
	 * @throws IOException 
	 */
	public static void loadFiles(String fileType, String folderPath, Date extractionDate, int numberOfDays, String delimiter, List<Data> dataList)
			throws IOException {

		final File folder = new File(folderPath);
		List<File> filesList = new ArrayList<>();

		filesList = filterUneededFiles(listFilesForFolder(folder), extractionDate, numberOfDays);

		for (File file : filesList) {
			if (file.isFile()) {
				dataList.addAll(loadData(fileType, file.getName(), file, delimiter));
			}
		}
	}

	/**
	 * Load Transactions files
	 * @param fileType
	 * @param filePath
	 * @param delimiter
	 * @return
	 * @throws IOException 
	 */
	public static List<Transaction> loadTransactionFiles(String folderPath, Date extractionDate, int numberOfDays, String delimiter) {

		final File folder = new File(folderPath);
		List<File> filesList = new ArrayList<>();
		List<Transaction> dataList = new ArrayList<Transaction>();

		filesList = filterUneededFiles(listFilesForFolder(folder), extractionDate, numberOfDays);

		for (File file : filesList) {
			if (file.isFile()) {
				dataList.addAll(loadTransactions(file, delimiter));
			}
		}

		return dataList;
	}

	/**
	 * Gets all files in the folder path and append them to filesList
	 * @param folder
	 * @param filesList
	 */
	public static List<File> listFilesForFolder(final File folder) {
		List<File> fullList = new ArrayList<>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				fullList.addAll(listFilesForFolder(fileEntry));
			} else {
				fullList.add(fileEntry);
			}
		}

		return fullList;
	}

	/**
	 * Filter files based on date in file name,  extractionDate and numberOfDays
	 * @param filesList
	 * @param extractionDate
	 * @param numberOfDays
	 * @return
	 */
	public static List<File> filterUneededFiles(List<File> filesList, Date extractionDate, int numberOfDays) {
		return 	filesList.stream().filter(file -> keepFile(file.getName(), extractionDate, numberOfDays)).collect(Collectors.toList());
	}

	/**
	 * Tells if the file should be filtered or not based on date in the file name,  extractionDate and numberOfDays
	 * @param fileName
	 * @param extractionDate
	 * @param numberOfDays
	 * @return
	 */
	public static boolean keepFile(String fileName, Date extractionDate, int numberOfDays) {
		String dateText = fileName.substring(fileName.length() - 13, fileName.length() - 5);

		LocalDate fileDate = LocalDate.parse(dateText, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate date = extractionDate.toLocalDate();
		return fileDate.isAfter(date.minusDays(numberOfDays)) && fileDate.isBefore(date.plusDays(1));
	}

	/**
	 * 
	 * @param fileType
	 * @param filePath
	 * @param delimiter
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<Data> loadData(String fileType, String fileName, File file, String delimiter)
			throws FileNotFoundException {
		List<Data> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(file);) {
			while(scanner.hasNextLine()) {
				records.add(DataFactory.getData(fileType, fileName, scanner.nextLine(), delimiter));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found : " + file.getPath());
			e.printStackTrace();
		}
		return records;
	}

	/**
	 * Load the transactions from a given file
	 * @param fileName
	 * @param file
	 * @param delimiter
	 * @return
	 */
	public static List<Transaction> loadTransactions(File file, String delimiter) {
		List<Transaction> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(file);) {
			while(scanner.hasNextLine()) {
				records.add(new Transaction(scanner.nextLine(), delimiter));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found : " + file.getPath());
			e.printStackTrace();
		}
		return records;
	}

	/**
	 * Saves data to the output file path up to maxDataSize
	 * @param mapData
	 * @param store
	 * @param date
	 * @param folderPath
	 * @param maxDataSize
	 * @throws FileNotFoundException
	 */
	public static void saveTopProdcut(Map<Integer, Integer> mapData, String store, Date date, String folderPath, int maxDataSize) throws FileNotFoundException {
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeFormatter.BASIC_ISO_DATE);
		String dateFormatted = date.toLocalDate().format(DateTimeFormatter.BASIC_ISO_DATE);

		String outputFilePath = folderPath + "top_100_ventes_" + store + "_" + dateFormatted + ".data";
		PrintWriter writer = new PrintWriter(new File(outputFilePath));

		mapData.entrySet().stream().limit(maxDataSize).forEach(data -> writer.write(data.getKey() + "|" + data.getValue() + "\r\n"));
		writer.close();
	}
}
