package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BoggleSolver {
	private String[][] boggleGrid;
	private String[] sorted;
	private ArrayList<String> foundWords = new ArrayList<String>();
	private HashSet<String> set = new HashSet<String>();
	
	/** 
	 * This class takes an Array representing a valid Boggle grid and an ArrayList containing
	 * all valid words for use in a game of Boggle and creates an ArrayList containing every
	 * possible valid word found in the grid.
	 * 
	 * @param boggleGrid	4 by 4 String array representing a standard 4 by 4 Boggle Grid			
	 * @param dictionary	list of words valid to use in a game of Boggle
	 */
	public BoggleSolver(String[][] boggleGrid, ArrayList<String> dictionary) {
		this.boggleGrid = boggleGrid;
		sorted = new String[dictionary.size()];
		dictionary.toArray(sorted);
		for (String i : dictionary) {
			set.add(i);
		}
		Arrays.sort(sorted);
		findAll();
	}
	
	/**
	 *  Finds all valid words in boggleGrid. Valid words are defined in sorted.
	 */
	private void findAll() {
		// used tracks the letters that have already been selected
		boolean[][] used = new boolean[4][4];
		// Calls the recursive findAll method on each position in boggleGrid
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				findAll(used, 0, y, x, "", 0);
			}
		}
	}
	
	/**
	 * Finds all valid words in boggleGrid. Valid words are defined in sorted.
	 * 
	 * @param used 	4 by 4 boolean array to track what letters have been used.
	 * @param k		the index of the last letter in str
	 * @param y		y position of the current letter
	 * @param x		x position of the current letter
	 * @param str	the previous, possibly valid String being created by selecting letters in boggleGrid
	 * @param index	the index in the sorted dictionary array that str can't logically be before alphabetically
	 */
	private void findAll(boolean[][] used, int k, int y, int x, String str, int index) {
		// Because "QU" is a possibility, k can be up to 16
		if (k > 16) {return;}
		
		// If k is too long, move down the array until a possibly valid word is reached
		while (k >= sorted[index].length()){
			index++;
		}
		
		// If the first char at boggleGrid[y][x] is alphabetically after the char at k at sorted[index],
		//   move down the array until this is not the case.
		while ((k < sorted[index].length()) && 
				(boggleGrid[y][x].charAt(0) > sorted[index].charAt(k))) {
			index++;
		}
		
		// Create a new copy of used to be passed to future recursive calls.
		boolean[][] recUsed = new boolean[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				recUsed[i][j] = used[i][j];
			}
		}
		
		// Here, we check to make sure nothing broke because of the above loops.
		//   k needs to be less than the length of the current word in sorted,
		//   the current letter from boggleGrid needs to match the corresponding letter at sorted[index],
		//   and the first part of sorted[index] needs to match str.
		if ((k < sorted[index].length()) && 
				boggleGrid[y][x].charAt(0) == sorted[index].charAt(k) && 
				sorted[index].substring(0, k).equals(str)) {
			
			// Taking care of the special "QU" case.
			if (boggleGrid[y][x].equals("QU")) {
				k++;
				while (k >= sorted[index].length()){index++;}
				while ((k < sorted[index].length()) && 
						(boggleGrid[y][x].charAt(1) > sorted[index].charAt(k))) {
					index++;
				}
				if ((k < sorted[index].length()) && boggleGrid[y][x].charAt(1) == sorted[index].charAt(k)) {
					recUsed[y][x] = true;
				} else {
					return;
				}
			}
			
			// Everything passed so everything gets updated.
			recUsed[y][x] = true;
			k++;
			str = str + boggleGrid[y][x];
		} else {
			return;
		}
		
		// Only words of length 3 or greater count for points and each word can only be used once.
		if (str.length() > 2 && set.contains(str) && !(foundWords.contains(str))) {
			foundWords.add(str);
		}
		
		// Continue the recursive calls by looping through all unused, adjacent indexes in boggleGrid.
		//   Diagonal counts as adjacent.
		for (int i = y - 1; i <= (y + 1); i++) {
			if (i >= 0 && i < 4) {
				for (int j = x - 1; j <= (x + 1); j++) {
					if (j >= 0 && j < 4) {
						if (!recUsed[i][j]) {
							findAll(recUsed, k, i, j, str, index);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Prints all words in foundWords to System.out
	 */
	public void printFound() {
		for (String i : foundWords) {
			if (i != null) {
				System.out.println(i);
			}
		}
	}
	
	/**
	 * Prints all words in sorted to System.out
	 */
	public void printD() {
		for (int x = 0; x < sorted.length; x++) {
			System.out.println(sorted[x]);
		}
	}
}
