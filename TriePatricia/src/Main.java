

import java.util.Scanner;

import trees.patricia.*;
import trees.Trie.Trie;

public class Main {

	public static void main(String[] args) {
		Trie set = new Trie();
		Scanner sc = new Scanner(System.in);
		String key = null;

		// Asks for the user input while not
		// an '.' is provided
		key = sc.next();
		while (key.charAt(0) != '.') {
			set.insert(key);
			key = sc.next();
		}

		// print results
		if (set.size() < 100) {
			System.out.println("keys(\"\"):");
			for (String retrivedKey : set) {
				System.out.println(retrivedKey);
			}
			System.out.println();
		}

		System.out.println("keysWithPrefix(\"a\"):");
		for (String s : set.keysWithPrefix("a"))
			System.out.println(s);

		System.out.println();

		System.out.println("keysWithPrefix(\"p\"):");
		for (String s : set.keysWithPrefix("p"))
			System.out.println(s);

		System.out.println();

		System.out.println("keysThatMatch(\"a..r\"):");
		for (String s : set.keysThatMatch("...ar"))
			System.out.println(s);

		sc.close();
		
		
		PatriciaTrie pt = new PatriciaTrie();
		pt.insert(10);
		pt.insert(200);
		pt.insert(100);
		pt.insert(210);
		pt.insert(50);
		pt.insert(90);
		pt.insert(101);
		pt.insert(215);
		pt.delete(200);
		
		pt.search(10);
	}
}
