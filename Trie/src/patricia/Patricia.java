package patricia;

public class Patricia<Type> {

	private PNode<Type> rootNode;
	private int count;

	public Patricia() {
		this.rootNode = new PNode<Type>();
		this.rootNode.value = null;
		this.rootNode.key = "";
		this.rootNode.compareAt = 0;
	}

	public void addNode(String key, Type value) {
		this.add(key, value, this.rootNode);
	}

	public void delNode(String key) throws Exception {
		
	}

	public Type getValue(String key) throws Exception {

		PNode<Type> n = this.get(key, this.rootNode, -1);

		if (n == null) throw new Exception("Node not found!");

		return n.value;
	}

	private PNode<Type> get(String key, PNode<Type> node, int parentCompareAt) {

		if (safeBitTest(key, node.compareAt)) {

			if (node.compareAt < parentCompareAt) {
				if (key.equals(node.key)) return node;

				if (node.left == null) return null;
			}

			return this.get(key, node.left, node.compareAt);
		}

		if (node.compareAt < parentCompareAt) {
			if (key.equals(node.key)) return node;

			if (node.right == null) return null;
		}

		return this.get(key, node.right, node.compareAt);
	}

	private void add(String key, Type value, PNode<Type> node) {

		// if
		if (safeBitTest(key, node.compareAt)) {

			if (node.left == null) {
				node.left = new PNode<Type>();
				node.left.compareAt = firstDifferingBit(key, node.key);
				node.left.key = key;
				node.left.value = value;
				this.count++;
				return;
			}

			this.add(key, value, node.left);
			return;
		}

		// else

		if (node.right == null) {
			node.right = new PNode<Type>();
			node.right.compareAt = firstDifferingBit(key, node.key);
			node.right.key = key;
			node.right.value = value;
			return;
		}

		this.add(key, value, node.right);
	}

	private static boolean safeBitTest(String key, int position) {
		if (position < key.length() * 16) return bitTest(key, position) != 0;
		if (position > key.length() * 16 + 15) return false; // padding
		/* 16 bits of 0xffff */
		return true; // end marker
	}

	private static int bitTest(String key, int position) {
		return (key.charAt(position >>> 4) >>> (position & 0xf)) & 1;
	}

	private static int safeCharAt(String key, int i) {
		if (i < key.length()) return key.charAt(i);
		if (i > key.length()) return 0x0000; // padding
		else return 0xffff; // end marker
	}

	private static int firstDifferingBit(String k1, String k2) {
		int i = 0;
		int c1 = safeCharAt(k1, 0) & ~1;
		int c2 = safeCharAt(k2, 0) & ~1;
		if (c1 == c2) {
			i = 1;
			while (safeCharAt(k1, i) == safeCharAt(k2, i))
				i++;
			c1 = safeCharAt(k1, i);
			c2 = safeCharAt(k2, i);
		}
		int b = 0;
		while (((c1 >>> b) & 1) == ((c2 >>> b) & 1))
			b++;
		return i * 16 + b;
	}

}
