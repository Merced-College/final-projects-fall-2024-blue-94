// Final Project - Cipher encryption and decryption
// Jeremiah Tenn
// Professor Kanemoto
// CPSC-39
// I wrote all lines in this program; it is original.

import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;


public class Main {
	// The regular alphabet - used to create the encoded alphabet
	public static char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	// HashMap for quick identifiction the encoded/decoded value for each letter
	public static HashMap<Character, Character> altAlphabet = new HashMap<Character, Character>();
	public static LinkedList<String> combo = new LinkedList<String>();

	// Returns a string encoded with the Ceasar cipher
	public static String encodeCeasar(String str, int key) {
		// Base word; encoded letters are added to this
		String encryptedWord = "";
		
		// Fills altAlphabet so each "regular" letter would be associated with its corresponding encoded letter
		for (int i = 0; i < 26; ++i) {
			// (i + key) % 26 is used so that keys of greater than 26 are allowed
			altAlphabet.put(letters[i], letters[(i + key) % 26]);
		}
		
		// Goes through each character in str and puts the corresponding encoded letter in encryptedWord
		for (int i = 0; i < str.length(); ++i) {
			// If the char is a space then do not change it
			if (str.charAt(i) == ' ') {
				encryptedWord = encryptedWord + " ";
				continue;
			}
			// If the character is uppercase: convert it to lowercase, find the corresponding letter and convert that to uppercase
			if (str.charAt(i) != Character.toLowerCase(str.charAt(i))) {
				encryptedWord = encryptedWord + Character.toString(Character.toUpperCase(altAlphabet.get(Character.toLowerCase(str.charAt(i)))));
				continue;
			}

			// Character.toString used since the individual letters are of type char
			encryptedWord = encryptedWord + Character.toString(altAlphabet.get(str.charAt(i)));
		}

		// Clear altAlphabet so that it may be used for other operations
		altAlphabet.clear();

		return encryptedWord;
	}
	
	// Decodes and returns a string given an Ceasar cipher encrypted word with a key
	public static String decodeCeasar(String str, int key) {
		// Each correspondiong letter is added to decryptedWord
		String decryptedWord = "";
		
		// Fills altAlphabet with encrypted letters with their corresponding "real" letter 
		for (int i = 0; i < 26; ++i) {
			// Note that the encrypted letters is the key and the regular letter is the value
		       	altAlphabet.put(letters[(i + key) % 26], letters[i]);
		}
		
		// Adds each decoded letter to the decryptedWord string 
		for (int i = 0; i < str.length(); ++i) {
			// If it is a space do not decrypt it, add it as-is
			if (str.charAt(i) == ' ') {
				decryptedWord = decryptedWord + " ";
				continue;
			}

			// Specific decryption for uppercase letters
			else if (str.charAt(i) != Character.toLowerCase(str.charAt(i))) {
				// Letter converted to lowercase, found in the hashmap and then converted to an uppercase string
				decryptedWord = decryptedWord + Character.toString(Character.toUpperCase(altAlphabet.get(Character.toLowerCase(str.charAt(i)))));
				continue;
			}

			// Standard decryption (no spaces or uppercase)
			decryptedWord = decryptedWord + Character.toString(altAlphabet.get(str.charAt(i)));
		}

		// Clears altAlphabet for future usage
		altAlphabet.clear();

		return decryptedWord;
	}
	
	// Finds all possibilities and adds them to the combo linked list
	public static void bruteForceDecodeCeasar(String word) {	
		// There are 26 unique keys (0-25) that could be possible, so the for loop decodes the word using each possible key
		for (int i = 0; i < 26; ++i) {
			// Adds the word to the linked list
			combo.add(decodeCeasar(word, i));
		}
	}

	// ROT13 is Ceasar Cipher with key of 13, so Ceasar Cipher is used to encode
	public static String encodeROT13(String word) {
		return encodeCeasar(word, 13); 
	}
	
	// Similarly, the Ceasar decryption algorithm is used for decoding a ROT13 message
	public static String decodeROT13(String word) {
		return decodeCeasar(word, 13);
	}
	
	// Encodes or decodes a word or phrase in Vigenere given a key of letters the same length		
	public static String encodeDecodeVigenere(String str, String key, boolean encrypt) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz"; // Used to locate the numerical value of each letter
		boolean encode = encrypt; 			// true if in encode mode, false if in decode mode
		String word = ""; 				// String that will be returned as the encoded phrase
		String resultLetter = ""; 			// Stores the encoded/decoded letter
		
		// Makes sure that the key is sufficient for the string message	
		if (str.length() != key.length()) {
			return "Message and string to not have the same length. Check the input.";
		}

		// Goes through each letter of the word and encodes/decodes it using the key
		for (int i = 0; i < str.length(); ++i) {

			// If the current character is a space simply add a space the string and continue
			if (str.charAt(i) == ' ') {
				word = word + " ";
				continue;
			}
			
			// Gets number value of the corresponding letter in the key
			int letterKey = alphabet.indexOf(key.charAt(i));
			// Encodes or decodes the letter and stores it in resultLetter
			if (encode) {
				resultLetter = encodeCeasar(str.substring(i, i + 1), letterKey);
			} else {
				resultLetter = decodeCeasar(str.substring(i, i + 1), letterKey);
			}
			// Checks if the letter should be uppercase and then adds it to word
			if (str.charAt(i) != Character.toLowerCase(str.charAt(i))) { 
				word = word + resultLetter.toUpperCase(); // Converted to uppercase and added to word
			} else {
				word = word + resultLetter; // Supposed to be lowercase so added without changes
			}
		}
		
		// Returns the final encoded string
		return word;
	}

	public static void recursiveVigenere(int[] array, int num) {
		// If the index number is greater than the array (resulting in a -1 index) then stop the current branch of recursion
		if (num > array.length) {
			return;
		}

		// for each possible letter combination:
		for (int i = 0; i < 26; ++i) {
			String word = "";
			// Uses the contents of the array to create a String key
			for (int j = 0; j < array.length; ++j) {
				// Finds the associated letter wth the current number int the array; adds it the the word string
				word  = word + Character.toString(letters[array[j]]);

			}
			
			// Places conditions for adding the word combination:
			// Add the word as long as the LinkedList is empty and not the same as the last node (we do not want duplicates)
			if (combo.size() == 0 || !word.equals(combo.getLast())) {
				combo.add(word);
			}

			// Create a copy of the array to be used in the recursive function; otherwise the array will be changed for all of the running threads
			int[] arrayCopy = new int[array.length];
			for (int j = 0; j < arrayCopy.length; ++j) {
				arrayCopy[j] = array[j];
			}
			
			// Run this function again using the copied list, except using the n-th + 1 to the last letter
			recursiveVigenere(arrayCopy, num + 1);
			// Adds one to the current index (letter) so that the next letter will be used.
			array[array.length - num] += 1;
		}
	}
	
	// Method for brute for Vigenere brute force decryption: requires a string and Scanner object
	public static void bruteForceDecodeVigenere(String str, Scanner scanner) {
		// Creates an array of integers the same length as the string
		int[] key = new int[str.length()];

		// Assigns every element of the array to zero, which represents "a"
		for (int i = 0; i < str.length(); ++i) {
			key[i] = 0;
		}
		// Run the recursive function to generate the possible combinations starting with the last letter
		System.out.println("Running....");
		recursiveVigenere(key, 1);
		
		// Iterate through the LinkedList of combonations and print them out
		// Creates a length variable to compare with in the loop, since combo.size() will be changing as the loop runs
		int length = combo.size();
		System.out.println("There are " + length + " combinations");
		System.out.println("Do you want to see them all? yes/no");
		
		if (scanner.nextLine().equals("yes")) {
			// For each element in the LinkedList's length:
			for (int i = 0; i < length; ++i) {
				// Removes the head from the LinkedList and prints it out
				System.out.println(combo.removeFirst());
			}
		}
		
		combo.clear();		// Ensure that the LinkedList is clear before next operation
	}
	

	public static void directory(Scanner scanner) {
		// Prints out the choices
		System.out.println("Enter a number:");

		System.out.println("1) Ceasar Cipher encryption");
		System.out.println("2) Ceasar Cipher decryption");
		System.out.println("3) Ceasar Cipher brute force decryption");

		System.out.println("4) ROT13 encryption");
		System.out.println("5) ROT13 decryption");

		System.out.println("6) Vigenère encryption");
		System.out.println("7) Vigenère decryption");
		System.out.println("8) Vigenère brute force decryption (single words only and may take a long time)");
		
		System.out.println("9) Quit");

		int choice = scanner.nextInt();
		scanner.nextLine(); 	// Read newline character (\n) from integer earlier; otherwise an error occurs
		
		if (choice == 9) {	// Quit
			return;
		}
		else if (choice < 1 || choice > 9) { 	// Ask for a choice again if invalid number	
		        System.out.println("Invalid choice");
	       		directory(scanner);
			return;
		}		
		
		System.out.println();	// Again, reading newline character
		System.out.println("Enter your message");	
		String message = scanner.nextLine();	// Get message to be encoded/decoded
		
		// Switch statement; easier to code and read than many if/else if statements
		switch(choice) {
			case 1:
				System.out.println("Enter your key");
				System.out.println(encodeCeasar(message, scanner.nextInt()));	// Gets the number key and uses in encodeCeasar
				scanner.nextLine();
				break;
			case 2:
				System.out.println("Enter the key:");
				System.out.println(decodeCeasar(message, scanner.nextInt())); 
				scanner.nextLine();
				break;
			case 3:
				bruteForceDecodeCeasar(message);	// Adds cominations to combo LinkedList
				System.out.println("Possible combinations:");
				for (int i = 0; i < 26; ++i) {		// Prints out each element of the LinkedList 
                        		System.out.println(combo.removeFirst());
                		}
				combo.clear();	// Makes sure that the LinkedList is empty
				break;
			// Case 4 and 5: print out returned string from method.
			case 4:
				System.out.println(encodeROT13(message));	
				break;
			case 5:
				System.out.println(decodeROT13(message));
				break;
			case 6:
				System.out.println("Enter your key:");
				System.out.println(encodeDecodeVigenere(message, scanner.nextLine(), true)); // Prints out the returned string using the input
				break;
			case 7:
				System.out.println("Enter your key:");
				System.out.println(encodeDecodeVigenere(message, scanner.nextLine(), false)); 
				break;
			case 8:
				bruteForceDecodeVigenere(message, scanner);	// Uses the message string and Scanner for brute force decryption. Results are printed from within the method
				break;
		}
		System.out.println();	// For space formatting
		directory(scanner);	// Calling directory method again so the user can choose new operations.
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);	// Creates scanner object that is used throughout the program
		directory(scanner);	// Presents choices to user and allows them to choose.
	}
}
