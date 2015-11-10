package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BoggleSolver {
	private String[][] boggleGrid;
	private ArrayList<String> dictionary;
	private String[] sortedD;
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
		this.dictionary = dictionary;
		sortedD = new String[dictionary.size()];
		for (String i : this.dictionary) {
			set.add(i);
		}
		for (int x = 0; x < dictionary.size(); x++) {
			sortedD[x] = dictionary.get(x);
		}
		Arrays.sort(sortedD);
		findAll();
	}
	
	@SuppressWarnings("unused")
	private void findAll() {
		boolean[][] used = new boolean[4][4];
		for (boolean[] i : used) {for (boolean j : i) {j = false;}}
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				findAll(used, 0, y, x, "", 1);
			}
		}
	}
	
	private void findAll(boolean[][] used, int k, int y, int x, String str, int index) {
		
		if (k >= 17) {return;}
		while (k >= sortedD[index].length()){
			index++;
		}
		while ((k < sortedD[index].length()) && 
				(boggleGrid[y][x].charAt(0) > sortedD[index].charAt(k))) {
			index++;
		}
		
		boolean[][] recUsed = new boolean[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				recUsed[i][j] = used[i][j];
			}
		}
		
		if ((k < sortedD[index].length()) && boggleGrid[y][x].charAt(0) == sortedD[index].charAt(k)) {
			if (boggleGrid[y][x] == "QU") {
				k++;
				while (k >= sortedD[index].length()){index++;}
				while ((k < sortedD[index].length()) && 
						(boggleGrid[y][x].charAt(1) > sortedD[index].charAt(k))) {
					index++;
				}
				if ((k < sortedD[index].length()) && boggleGrid[y][x].charAt(1) == sortedD[index].charAt(k)) {
					recUsed[y][x] = true;
				} else {
					return;
				}
			}
			recUsed[y][x] = true;
			k++;
			str = str + boggleGrid[y][x];
		} else {
			return;
		}
		
		if (str.length() > 2 && set.contains(str) && !(foundWords.contains(str))) {
			foundWords.add(str);
		}
		
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
	
	public void printFound() {
		for (String i : foundWords) {
			if (i != null) {
				System.out.println(i);
			}
		}
	}
	
	public void printD() {
		for (int x = 0; x < sortedD.length; x++) {
			System.out.println(sortedD[x]);
		}
	}
}
