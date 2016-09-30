// Terran Jendro, CSE
// 143, Section BG

// HuffmanTree allows for file compression and decompression. It can take a file and encode
// its contents in a .zip fashion and take the same file and decode it to return its contents
// to its original state. 

import java.util.*;
import java.io.*;

public class HuffmanTree {

	private Queue<HuffmanNode> huffmanSort;
	private HuffmanNode root;
   
 	// Pre: counts is not null. Throws IllegalArgumentException. Creates new HuffmanTree
	// out of given letter frequencies. Gets ready for file compressing.
	public HuffmanTree(int[] counts) {
		if (counts == null) {
			throw new IllegalArgumentException();
		}
		huffmanSort = new PriorityQueue<HuffmanNode>();
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) { // If letters are in the file, add them to Tree
				huffmanSort.add(new HuffmanNode((char) i, counts[i]));
			}
		} // Adding EOF Char.
		huffmanSort.add(new HuffmanNode((char) (counts.length), 1));
		huffmanSort = createTree(huffmanSort); // Creates HuffmanTree
	}
	
	// Writes results of HuffmanTree in coded format. Creates the format to be easily
    // decoded.
	public void write(PrintStream output) {
		write(huffmanSort.remove(), output, new ArrayList<Integer>());
	}
	
	// Constructs a new HuffmanTree that takes an input of a coded tree and reconstructs
	// the HuffmanTree to allow for decoding.
	public HuffmanTree(Scanner input) {
		root = new HuffmanNode((char) 0, 0);
		while (input.hasNextLine()) {
			int asc = Integer.parseInt(input.nextLine());
			String code = input.nextLine();
			root = constructTree(asc, code, root);
		}
	}
	
	// Decodes a compressed HuffmanTree file. After this is called, reads encoded file and
	// Runs through BST to decode file to its correct state.
	public void decode(BitInputStream input, PrintStream output, int eof)
	{
		HuffmanNode find = root;
		while ((int)find.getChar() != eof) {
			int next = input.readBit();
			if (find.rightOne == null && find.leftZero == null) {
				output.print(find.getChar()); // Output character
				find = root;   // Reset find to read next set of bits
			}
			if (next == 0) {
				find = find.leftZero;
			} else {
				find = find.rightOne;
			}
		}
	}
   
    // Creates the tree of letters and frequencies. After call, the letters and
    // frequencies are stored in a write-ready format.
	private Queue<HuffmanNode> createTree(Queue<HuffmanNode> root) {
		if (root.size() > 1) {
			HuffmanNode first = root.remove();
			HuffmanNode second = root.remove();
			root.add(new HuffmanNode((char) 0, first.getFrequence() + second.getFrequence(),
					first, second));
			root = createTree(root);
		}
		return root;
	}
   
	// Private helper method for write. Writes out HuffmanTree in coded format such that
	// the letter identity is written followed by its location in the BST.
	private void write(HuffmanNode root, PrintStream output, List<Integer> list) {
		if (root.leftZero == null && root.rightOne == null) {
			output.println((int) root.getChar()); // Outputs char int value
			for (int i = 0; i < list.size(); i++) {
				output.print(list.get(i)); // Outputs binary line location of char
			}
			output.println();
		} else { // Recurs tree and assigns 0's to left branches and 1's to right's
			list.add(0);
			write(root.leftZero, output, list);
			list.remove(list.size() - 1);
			list.add(1);
			write(root.rightOne, output, list);
			list.remove(list.size() - 1);
		}
	}

	// Constructs a tree given a set of binary paths and integer ASC values. After called, a 
	// BST stores the values of letters in a file and their binary identities.
	private HuffmanNode constructTree(int asc, String code, HuffmanNode root) { 
		if (code.length() <= 1) {
			if (code.charAt(0) == '0') { // Stores node ASC value in left node
				root.leftZero = new HuffmanNode((char) asc, -1);
			} else { // Stores node ASC value in right node
				root.rightOne = new HuffmanNode((char) asc, -1);
			}
		} else if (code.charAt(0) == '0' && code.length() > 1) {
			if (root.leftZero == null) { // If node is null, create new node location for left
				root.leftZero = constructTree(asc, code.substring(1, code.length()),
						new HuffmanNode((char) 0, 0));
			} else { // If node is not null, go left to the next location
				root.leftZero = constructTree(asc, code.substring(1, code.length()),
						root.leftZero);
			}
		} else if (code.charAt(0) == '1' && code.length() > 1) {
			if (root.rightOne == null) { // If node is null, create new node location for right
				root.rightOne = constructTree(asc, code.substring(1, code.length()),
						new HuffmanNode((char) 0, 0));
			} else { // If node is not null, go right to the next location
				root.rightOne = constructTree(asc, code.substring(1, code.length()),
						root.rightOne);
			}
		}
		return root;
	}
   
	// Private class that is the backbone for our HuffmanTree. Stores the letter and
	// given frequency of that letter in a file and references the next left node and 
	// right node, resembling a BST.
	private class HuffmanNode implements Comparable<HuffmanNode> {
		private char letter;
		private int frequence;
		public HuffmanNode rightOne;
		public HuffmanNode leftZero;
      
		// Constructs new HuffmanNode with given letter and frequence
		public HuffmanNode(char letter, int frequence) {
			this(letter, frequence, null, null);
		}
      
		// Constructs new HuffmanNode with given letter, frequence,
		// and references to left and right nodes.
		public HuffmanNode(char letter, int frequence, HuffmanNode leftZero, HuffmanNode rightOne){
			this.letter = letter;
			this.frequence = frequence;
			this.leftZero = leftZero;
			this.rightOne = rightOne;
		}
      
		// Gets frequence of letter
		public int getFrequence() {
			return frequence;
		}
      
		// Gets char value.
		public char getChar() {
			return letter;
		}
      
		// Compares two HuffmanNodes to each other.
		public int compareTo(HuffmanNode other) {
			return frequence - other.frequence;
		}
      
		// Typical toString
		public String toString() {
			return "(" + letter + " : " + frequence + ")";
		}
	}
}