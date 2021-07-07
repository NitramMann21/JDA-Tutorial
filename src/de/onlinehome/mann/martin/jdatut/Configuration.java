package de.onlinehome.mann.martin.jdatut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Configuration {
	
	private static String token = "";
	private static String bloep = "";
	
	public static void read() throws FileNotFoundException {
		File file = new File("config.cfg");
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] splittedLine = new String[2];
				splittedLine = line.split(": ", 2);
				
				switch (splittedLine[0]) {
				case "TOKEN":
					token = splittedLine[1]; break;
				case "BLOEP":
					bloep = splittedLine[1]; break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getToken() { return token; }
	public static String getBloep() { return bloep; }
	
}
