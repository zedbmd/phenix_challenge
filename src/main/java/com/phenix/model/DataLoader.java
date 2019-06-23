package com.phenix.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class DataLoader {

	/**
	 * 
	 * @param fileType
	 * @param filePath
	 * @param delimiter
	 * @return
	 * @throws IOException 
	 */
	public static void loadFiles(String fileType, String folderPath, String delimiter, List<Data> dataList)
			throws IOException {

		final File folder = new File(folderPath);
		List<File> filesList = new ArrayList<>();

		listFilesForFolder(folder, filesList);

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
	public static void listFilesForFolder(final File folder, List<File> filesList) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, filesList);
			} else {
				filesList.add(fileEntry);
			}
		}
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
}
