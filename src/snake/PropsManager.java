package snake;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropsManager {
	
	private PropsManager() {}
	
	public static Properties getProperties() {
		
		try {
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("snake.properties");
			prop.load(in);
			in.close();
			return prop;
		}catch(FileNotFoundException fe) {
			System.out.println("Could not load file.");
		}catch(IOException ie) {
			System.out.println("Could not load properties.");
		}
		
		return null;
	}
	
	public static void storeProperty(Properties prop) {
		
		try {
			FileOutputStream out = new FileOutputStream("snake.properties");
			prop.store(out, "");
			out.close();
		}catch(FileNotFoundException fe) {
			System.out.println("Could not find file to write to.");
		}catch(IOException ie) {
			System.out.println("Could not store properties.");
		}
	}
}
