package trees.Trie;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/*****************************************************************************
 * Implementation of a Trie
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/52trie">Section 5.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * 
 * Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 * 
 * This file is part of algs4.jar, which accompanies the textbook
 * 
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 * 
 * algs4.jar is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * algs4.jar is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * algs4.jar. If not, see http://www.gnu.org/licenses.
 * 
 * @author Kevin Wayne (main author)
 * @author Addison-Wesley (main author)
 * @author Robert Sedgewick (main author)
 * @author André Furlan <ensismoebius@gmail.com> (just made it more readable)
 *
 ******************************************************************************/
public class Trie implements Iterable<String> {
	private static final int constAlphabetSize = 256; // extended ASCII

	private TrieNode trieRoot; // root of trie
	private int amountOfKeysStored; // number of keys in trie

	/**
	 * Does the set contain the given key?
	 * 
	 * @param key the key
	 * @return {@code true} if the set contains {@code key} and {@code false}
	 *         otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");

		TrieNode x = get(trieRoot, key, 0);

		if (x == null) return false;

		return x.isLeaf();
	}

	private TrieNode get(TrieNode x, String key, int trieLevel) {
		if (x == null) return null;
		if (trieLevel == key.length()) return x;
		char c = key.charAt(trieLevel);
		return get(x.getArrSubTries()[c], key, trieLevel + 1);
	}

	/**
	 * Adds the key to the set if it is not already present.
	 * 
	 * @param key the key to add
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void add(String key) {
		if (key == null) throw new IllegalArgumentException("argument to add() is null");
		trieRoot = add(trieRoot, key, 0);
	}

	private TrieNode add(TrieNode x, String key, int trieLevel) {
		// if the does not exists create one
		if (x == null) x = new TrieNode(constAlphabetSize);

		// The leaf has been reached, return
		// the recursive calls and one to
		// key stored
		if (trieLevel == key.length()) {

			// if the key already exists DO NOT increments keys amount
			if (!x.isLeaf()) this.amountOfKeysStored++;

			// Mark it as a leaf
			x.setLeaf(true);

			return x;
		}

		// no leaf has been reached yet
		// go to or create the next trie level
		char c = key.charAt(trieLevel);
		x.getArrSubTries()[c] = add(x.getArrSubTries()[c], key, trieLevel + 1);

		return x;
	}

	/**
	 * Returns the number of strings in the set.
	 * 
	 * @return the number of strings in the set
	 */
	public int size() {
		return this.amountOfKeysStored;
	}

	/**
	 * Is the set empty?
	 * 
	 * @return {@code true} if the set is empty, and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return this.amountOfKeysStored == 0;
	}

	/**
	 * Returns all of the keys in the set, as an iterator. To iterate over all of
	 * the keys in a set named {@code set}, use the foreach notation:
	 * {@code for (Key key : set)}.
	 * 
	 * @return an iterator to all of the keys in the set
	 */
	public Iterator<String> iterator() {
		return keysWithPrefix("").iterator();
	}

	/**
	 * Returns all of the keys in the set that start with {@code prefix}.
	 * 
	 * @param prefix the prefix
	 * @return all of the keys in the set that start with {@code prefix}, as an
	 *         iterable
	 */
	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> results = new LinkedList<String>();
		TrieNode node = get(trieRoot, prefix, 0);
		collect(node, new StringBuilder(prefix), results);
		return results;
	}

	private void collect(TrieNode x, StringBuilder prefix, Queue<String> results) {
		if (x == null) return;
		if (x.isLeaf()) results.add(prefix.toString());
		for (char c = 0; c < constAlphabetSize; c++) {
			prefix.append(c);
			collect(x.getArrSubTries()[c], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	/**
	 * Returns all of the keys in the set that match {@code pattern}, where . symbol
	 * is treated as a wildcard character.
	 * 
	 * @param pattern the pattern
	 * @return all of the keys in the set that match {@code pattern}, as an
	 *         iterable, where . is treated as a wildcard character.
	 */
	public Iterable<String> keysThatMatch(String pattern) {
		Queue<String> results = new LinkedList<String>();
		StringBuilder prefix = new StringBuilder();
		collect(trieRoot, prefix, pattern, results);
		return results;
	}

	private void collect(TrieNode x, StringBuilder prefix, String pattern, Queue<String> results) {
		if (x == null) return;
		int d = prefix.length();
		if (d == pattern.length() && x.isLeaf()) results.add(prefix.toString());
		if (d == pattern.length()) return;
		char c = pattern.charAt(d);
		if (c == '.') {
			for (char ch = 0; ch < constAlphabetSize; ch++) {
				prefix.append(ch);
				collect(x.getArrSubTries()[ch], prefix, pattern, results);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		} else {
			prefix.append(c);
			collect(x.getArrSubTries()[c], prefix, pattern, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	/**
	 * Returns the string in the set that is the longest prefix of {@code query}, or
	 * {@code null}, if no such string.
	 * 
	 * @param query the query string
	 * @return the string in the set that is the longest prefix of {@code query}, or
	 *         {@code null} if no such string
	 * @throws IllegalArgumentException if {@code query} is {@code null}
	 */
	public String longestPrefixOf(String query) {
		if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
		int length = longestPrefixOf(trieRoot, query, 0, -1);
		if (length == -1) return null;
		return query.substring(0, length);
	}

	// returns the length of the longest string key in the subtrie
	// rooted at x that is a prefix of the query string,
	// assuming the first d character match and we have already
	// found a prefix match of length length
	private int longestPrefixOf(TrieNode x, String query, int d, int length) {
		if (x == null) return length;
		if (x.isLeaf()) length = d;
		if (d == query.length()) return length;
		char c = query.charAt(d);
		return longestPrefixOf(x.getArrSubTries()[c], query, d + 1, length);
	}

	/**
	 * Removes the key from the set if the key is present.
	 * 
	 * @param key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(String key) {
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		trieRoot = delete(trieRoot, key, 0);
	}

	private TrieNode delete(TrieNode x, String key, int d) {
		if (x == null) return null;
		if (d == key.length()) {
			if (x.isLeaf()) amountOfKeysStored--;
			x.setLeaf(false);
		} else {
			char c = key.charAt(d);
			x.getArrSubTries()[c] = delete(x.getArrSubTries()[c], key, d + 1);
		}

		// remove subtrie rooted at x if it is completely empty
		if (x.isLeaf()) return x;
		for (int c = 0; c < constAlphabetSize; c++)
			if (x.getArrSubTries()[c] != null) return x;
		return null;
	}
}
