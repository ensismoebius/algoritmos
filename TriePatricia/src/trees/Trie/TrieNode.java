package trees.Trie;

/*****************************************************************************
 * Implementation of a TrieNode
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
public class TrieNode {
	private TrieNode[] arrSubTries;
	private boolean isLeaf = false;

	public TrieNode(int alphabetSize) {
		this.arrSubTries = new TrieNode[alphabetSize];
	}

	public TrieNode[] getArrSubTries() {
		return arrSubTries;
	}

	public void setArrSubTries(TrieNode[] arrSubTries) {
		this.arrSubTries = arrSubTries;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public String toString() {
		return "IsLeaf: " + this.isLeaf + " Subs: " + this.arrSubTries.length;
	}
}
