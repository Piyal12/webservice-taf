package com.im.service.util.other;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class Strings {
	
	private static final String ALPHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
	private static final String NUMERIC ="0123456789";
	private static final String ALPHA_NUMERIC ="A1a2Bb3Cc4Dd5Ee6Ff7Gg8Hh9I0iJ1jK2kL3lM4mN5nO6oP7pQ8qR9rS0sT1tU2uV3vW4wX5xY6yZ7z0123456789";
	private static final String SPECIAL_CHAR = "!@#$%^&*()_+-=[]`{},.<>/?";
	
	private static int defLength = 5;
	
	public static void setLen (int length) {
		Strings.defLength = length;
	}
	
	public static int getLen () {
		return Strings.defLength;
	}
	
	public static int getRandomInteger (int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static String getRandomString () {
		String str = "";
		for (int i=0; i<Strings.getLen(); i++) 
			str += Strings.ALPHABET.charAt(getRandomInteger (0, Strings.ALPHABET.length() - 1));
		return str;
	}
	
	public static String getRandomNumber () {
		String str = "";
		for (int i=0; i<Strings.getLen(); i++) 
			str += Strings.NUMERIC.charAt(getRandomInteger (0, Strings.NUMERIC.length() - 1));
		return str;
	}
	
	public static String getRandomSpecialChar () {
		String str = "";
		for (int i=0; i<Strings.getLen(); i++) 
			str += Strings.SPECIAL_CHAR.charAt(getRandomInteger (0, Strings.SPECIAL_CHAR.length() - 1));
		return str; 
	}
	public static String getRandomEmail () {
		Faker faker=new Faker();
		return faker.internet().emailAddress();
	}
	
	public static String getRandomAlphaNumeric () {
		String str = "";
		for (int i=0; i<Strings.getLen(); i++) 
			str += Strings.ALPHA_NUMERIC.charAt(getRandomInteger (0, Strings.ALPHA_NUMERIC.length() - 1));
		return str;
	}
	
	public static String getEpochTime () {
		return Instant.now().getEpochSecond()+"";
	}
	
	public static String getDateString () {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");  
	    return formatter.format(date);  
	}
	
}
