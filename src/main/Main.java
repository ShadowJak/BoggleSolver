package main;

import core.BoggleSolver;
import io.ReadFile;

public class Main {

	public static void main(String[] args) {
		// This is sample input.
		String[][] boggleGrid = new String[][]{
			{"T","O","P","U"},
			{"H","I","S","E"},
			{"QU","E","N","K"},
			{"R","W","A","C"},
		};
		
		// Name of the text file which contains the valid words
		String dictionaryFileName = "AllWordsFixed.txt";
		// Initialize a ReadFile obj with the name of the dictionary text file.
		ReadFile dictionaryData = new ReadFile(dictionaryFileName);
		
		// Retrieve the data from the text file and store it in an ArrayList in dictionaryData
		dictionaryData.populateData();
		
		// Initialize an Invictus object with the 4x4 string array and the dictionary ArrayList
		BoggleSolver invictus = new BoggleSolver(boggleGrid, dictionaryData.getData());
		
		// Prints every word found to syso on its own line.
		invictus.printFound();
		
	}

}
