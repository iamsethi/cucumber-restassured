package com.api.managers;

import com.amazon.dataProviders.ConfigFileReader;
import com.amazon.dataProviders.JsonDataReader;

public class FileReaderManager {// Singleton Design Pattern

	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;
	private static JsonDataReader jsonDataReader;

	private FileReaderManager() {
	}

	public static FileReaderManager getInstance() {
		return fileReaderManager;
	}

	public ConfigFileReader getConfigReader() {
		return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
	}

	public JsonDataReader getJsonReader() {
		return (jsonDataReader == null) ? new JsonDataReader() : jsonDataReader;
	}
}