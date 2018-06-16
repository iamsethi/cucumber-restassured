package com.amazon.dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.api.managers.FileReaderManager;

public class ServiceFileReader {
	private Properties properties;
	private final String serviceFilePath = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath()
			+ "service.properties";

	public ServiceFileReader() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(serviceFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Properties file not found at path : " + serviceFilePath);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ignore) {
			}
		}
	}

	public String getServiceEndPoint(String endPoint) {
		String serviceEndPoint = properties.getProperty(endPoint);
		if (serviceEndPoint != null)
			return serviceEndPoint;
		else
			throw new RuntimeException("Service End Point not specified in the service.properties file for the Key:"
					+ serviceEndPoint + "");
	}

}
