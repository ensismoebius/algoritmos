package trees.patricia;

/* Class PatriciaTrie Test */

public class PatriciaTrieTest {

	public static void main(String[] args) {
		PatriciaTrie pt = new PatriciaTrie();
		pt.insert(10);
		pt.insert(200);
		pt.insert(100);
		pt.insert(210);
		pt.insert(50);
		pt.insert(90);
		pt.insert(101);
		pt.delete(200);
	}
}