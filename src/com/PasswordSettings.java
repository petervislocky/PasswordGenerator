package com;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PasswordSettings {
	
	private Properties properties;
	
	public PasswordSettings() {
		this.properties = new Properties();
		loadProperties();
	}
	
	public void loadProperties() {
		try {
			FileInputStream fileInput = new FileInputStream(System.getProperty("user.home") + "/passwordSettings.properties");
			properties.load(fileInput);
			fileInput.close();
		}
		catch (IOException e) {
			System.out.println("Error occured with file input. Stack trace: ");
			e.printStackTrace();
			
			//if the properties file doesn't exist or can't be loaded
			setDefaultProperties();
			
		}
	}
	
	public void saveProperties() {
		try {
			FileOutputStream output = new FileOutputStream(System.getProperty("user.home") + "/passwordSettings.properties");
			properties.store(output, "User Password Settings");
			output.close();
		}
		catch (IOException e) {
			System.out.println("Error occured with file output. Stack trace: ");
			e.printStackTrace();
		}
	}
	
	public void setDefaultProperties() {
		properties.setProperty("upperLetters", "true");
		properties.setProperty("lowerLetters", "true");
		properties.setProperty("nums", "true");
		properties.setProperty("symbols", "true");
		saveProperties();
		
	}
	
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public boolean getProperty(String key) {
		String value = properties.getProperty(key);
		if (value != null) {
			return Boolean.parseBoolean(value);
		}
		//returning a default value of true if the key doesn't exist
		return true;
	}
}
