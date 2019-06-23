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

public final class DataManager {
	final static String LOCAL_DATE_FORMAT = "yyyyMMdd";

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
	 * 
	 * @param filesList
	 * @param extractionDate
	 * @param numberOfDays
	 * @return
	 */
	public static List<File> filterUneededFiles(List<File> filesList, Date extractionDate, int numberOfDays) {
		return 	filesList.stream().filter(file -> keepFile(file.getName(), extractionDate, numberOfDays)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param fileName
	 * @param extractionDate
	 * @param numberOfDays
	 * @return
	 */
	public static boolean keepFile(String fileName, Date extractionDate, int numberOfDays) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);
		String dateText = fileName.substring(fileName.length() - 13, fileName.length() - 5);

		LocalDate fileDate = LocalDate.parse(dateText, formatter);
		LocalDate date = extractionDate.toLocalDate();

		return date.isAfter(date.minusDays(numberOfDays)) && fileDate.isBefore(date.plusDays(1));
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
		List<Data> records = new ArrayList<Data>();
		try (Scanner scanner = new Scanner(file);) {
			while(scanner.hasNextLine()) {
				records.add(DataFactory.getData(fileType, fileName, scanner.nextLine(), delimiter));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found : " + file.getPath());
			e.printStackTrace();
		}

		//System.out.println(records.toString());
		return records;
	}

	/**
	 * Saves data to the output file path up to maxDataSize
	 * @param mapData
	 * @param outputFilePath
	 * @throws FileNotFoundException
	 */
	public static void saveData(Map<String, String> mapData , String outputFilePath, int maxDataSize) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(outputFilePath));

		mapData.entrySet().stream().limit(maxDataSize).forEach(data -> writer.write(data.getKey() + "|" + data.getValue() + "\r\n"));
		writer.close();
	}
}
