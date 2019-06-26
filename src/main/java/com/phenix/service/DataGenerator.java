package com.phenix.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.phenix.model.Data;
import com.phenix.model.DataFactory;

public class DataGenerator {

	private String dataType;
	private String dataFolder;
	private int maxValues;
	
	private String store;
	private Date date;

	public DataGenerator(String dataType, String dataFolder, Date date, String store, int maxValues) {
		this.dataType = dataType;
		this.dataFolder = dataFolder;
		this.maxValues = maxValues;
		this.store = store;
		this.date = date;
	}

	public void generate() {
		List<Data> dataList = getdataList();

		writeData(dataList);
	}

	private List<Data> getdataList() {
		List<Data> dataList = new ArrayList<>();

		for(int i = 0; i < this.maxValues; i++) {
			Data data = DataFactory.getData(this.dataType, date, store);
			dataList.add(data);
		}

		return dataList;
	}

	private void writeData(List<Data> dataList) {
		try {
			PrintWriter writer = new PrintWriter(new File(this.dataFolder + DataFactory.getFileName(this.dataType, date, store)));
			dataList.stream().forEach(data -> writer.write(data.getLine()));
			writer.close(); 
		} catch (FileNotFoundException e) {
			System.err.println("File not found :" + this.dataFolder);
			e.printStackTrace();
		}
	}
}
