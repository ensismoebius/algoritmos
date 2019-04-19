package trees.Trie;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Trie set = new Trie();
		Scanner sc = new Scanner(System.in);
		String key = null;

		// Asks for the user input while not
		// an '.' is provided
		key = sc.next();
		while (key.charAt(0) != '.') {
			set.add(key);
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

		System.out.println("longestPrefixOf(\"am\"):");
		System.out.println(set.longestPrefixOf("am"));
		System.out.println();

		System.out.println("longestPrefixOf(\"pe\"):");
		System.out.println(set.longestPrefixOf("pe"));
		System.out.println();

		System.out.println("keysWithPrefix(\"a\"):");
		for (String s : set.keysWithPrefix("a"))
			System.out.println(s);

		System.out.println();

		System.out.println("keysWithPrefix(\"p\"):");
		for (String s : set.keysWithPrefix("p"))
			System.out.println(s);

		System.out.println();

		System.out.println("keysThatMatch(\"a..r\"):");
		for (String s : set.keysThatMatch("...do"))
			System.out.println(s);

		sc.close();
	}
}
