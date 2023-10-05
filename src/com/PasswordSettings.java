package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PasswordSettings {
	
	private Properties properties;
	private String configFolderPath;
	
	public PasswordSettings() {
		this.properties = new Properties();
		this.configFolderPath = System.getProperty("user.home") + File.separator + "PasswordGenConfig"; 
		loadProperties();
	}
	
	public void loadProperties() {
		try {
			File configFolder = new File(configFolderPath);
			//creates config folder if it does not exist
			if (!configFolder.exists()) {
				configFolder.mkdir();
			}
			FileInputStream fileInput = new FileInputStream(configFolderPath + File.separator + "passwordSettings.properties");
			properties.load(fileInput);
			fileInput.close();
		}
		catch (FileNotFoundException e) {
			/*this usually won't even occur but in some rare instances, if the save file doesn't exist it will throw this exception before creating one with default values
			 * it doesn't effect the way the progam runs either way though.
			 */
			System.out.println("No save file detected. Creating settings file to save settings");
			setDefaultProperties();
		}
		catch (IOException e) {
			System.err.println("IOException occured. Stack trace: ");
			e.printStackTrace();
			
		}
	}
	
	public void saveProperties() {
		try {
			File configFolder = new File(configFolderPath);
			if (!configFolder.exists()) {
				configFolder.mkdir();
			}
			FileOutputStream output = new FileOutputStream(configFolderPath + File.separator + "passwordSettings.properties");
			properties.store(output, "User Password Settings");
			output.close();
		}
		catch (IOException e) {
			System.err.println("IOException occured. Stack trace: ");
			e.printStackTrace();
		}
	}
	
	//sets default properties if no properties exist/the .properties file doesn't exist
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
