package io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
	private InputStream input;
	private Scanner in;
	private String fileName;
	private ArrayList<String> data = new ArrayList<String>();
	
	public ReadFile(String fileName) {
		this.fileName = fileName;
	}
	
	public void populateData() {
		try {
			input = getClass().getResourceAsStream(fileName);
			in = new Scanner(input);
			
			while (in.hasNext()) {
				data.add(in.next());
			}
		} catch(Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			System.out.println(fileName);
		}
	}
	
	public ArrayList<String> getData() {
		return data;
	}
}
