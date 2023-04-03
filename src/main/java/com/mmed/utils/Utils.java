package com.mmed.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Utils {
	
	private Utils() {
		
	}

	public static String getCurrentPath() {
		String filepath = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		filepath = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return filepath;
	}
	
	public static String getPropertyValue(String key) throws IOException{
		String value = null;
		Properties prop = new Properties();  
		try(FileReader reader = new FileReader("config.properties")) {
			prop.load(reader);  	
			value = prop.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return value; 
	}
	
	public static String encryptMD5(String input)
	{
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}	
}
