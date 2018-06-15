package com.amazon.dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.api.managers.FileReaderManager;
import com.api.testDataTypes.Customer;
import com.google.gson.Gson;

public class JsonDataReader {
	private final String dataFilePath = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath()
			+ "data.json";
	private List<Customer> dataList;

	public JsonDataReader() {
		dataList = getCustomerData();
	}

	private List<Customer> getCustomerData() {
		Gson gson = new Gson();
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(dataFilePath));
			Customer[] customers = gson.fromJson(bufferReader, Customer[].class);
			return Arrays.asList(customers);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Json file not found at path : " + dataFilePath);
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException ignore) {
			}
		}
	}

	public final Customer getDataById(String customerName) {
		return dataList.stream().filter(x -> x.firstName.equalsIgnoreCase(customerName)).findAny().get();
	}

}