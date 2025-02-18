package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;

	public ConfigReader() {
		properties = new Properties();
		loadProperties();
	}

	private void loadProperties() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return;
			}
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
