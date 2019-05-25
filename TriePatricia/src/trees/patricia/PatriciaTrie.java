package trees.patricia;
/* Class PatriciaTrie */

public class PatriciaTrie {

	private PatriciaNode root;
	private static final int MaxBits = 10;

	public PatriciaTrie() {
		root = null;
	}

	/** function to check if empty **/

	public boolean isEmpty() {
		return root == null;
	}

	/** function to clear **/

	public void makeEmpty() {
		root = null;
	}

	public boolean search(int value) {

		int numOfBits = (int) (Math.log(value) / Math.log(2)) + 1;

		if (numOfBits > MaxBits) {
			System.out.println("Error : Number too large");
			return false;
		}

		PatriciaNode searchNode = search(root, value);

		if (searchNode.data == value) {
			return true;
		} else {
			return false;
		}
	}

	private PatriciaNode search(PatriciaNode node, int value) {

		PatriciaNode currentNode, nextNode;

		if (node == null) return null;

		currentNode = node;
		nextNode = node.leftChild;

		String binaryValue = toBinary(value);
		while (nextNode.bitNumber > currentNode.bitNumber) {
			currentNode = nextNode;
			nextNode = isBit1At(binaryValue, nextNode.bitNumber) ? nextNode.rightChild : nextNode.leftChild;
		}

		return nextNode;
	}

	public void insert(int value) {

		int numOfBits = (int) (Math.log(value) / Math.log(2)) + 1;

		if (numOfBits > MaxBits) {
			System.out.println("Error : Number too large");
			return;
		}

		root = insert(this.root, value);
	}

	private PatriciaNode insert(PatriciaNode node, int value) {

		int differingBitNumber;
		String binaryValue, binaryLastNodeValue;
		PatriciaNode newNodeGrandma, newNodeParent, lastNode, newNode;

		// Tree is empty create the first item
		if (node == null) {
			node = new PatriciaNode();

			node.bitNumber = 0;
			node.data = value;
			node.leftChild = node;
			node.rightChild = null;

			return node;
		}

		// Checks if there is already a node with this value
		lastNode = search(node, value);
		if (value == lastNode.data) {
			System.out.println("Error : key is already present\n");
			return node;
		}

		// There is no value like this stored!!!
		// translate values in to the binary
		// representations for comparisons
		binaryValue = toBinary(value);
		binaryLastNodeValue = toBinary(lastNode.data);

		// find the first differing bit beetween the binary representations
		differingBitNumber = 1;
		while (isBit1At(binaryValue, differingBitNumber) == isBit1At(binaryLastNodeValue, differingBitNumber)) {
			differingBitNumber++;
		}

		// going down in the tree in order to find a spot to insert the new node
		newNodeParent = node;
		newNodeGrandma = node.leftChild; // I known it makes no sense, but after the loop will
		while (newNodeGrandma.bitNumber > newNodeParent.bitNumber && newNodeGrandma.bitNumber < differingBitNumber) {
			newNodeParent = newNodeGrandma;

			// deciding if we should go to the left or to ther right
			// bit 1 to right, bit 0 to left
			newNodeGrandma = isBit1At(binaryValue, newNodeGrandma.bitNumber) ? newNodeGrandma.rightChild : newNodeGrandma.leftChild;
		}

		// spot has been found!!
		// creating the node
		newNode = new PatriciaNode();
		newNode.bitNumber = differingBitNumber;
		newNode.data = value;

		// Doing some circular references, it will be used when we search at tree
		// when bitNumberCurrent < bitnumberParent we known we reach the bottom of
		// the tree

		// my grandma is less than me? Put it on the left otherwise put it on the right
		newNode.leftChild = isBit1At(binaryValue, differingBitNumber) ? newNodeGrandma : newNode;

		// my grandma is bigger than me? Put it on right otherwise put it on the left
		newNode.rightChild = isBit1At(binaryValue, differingBitNumber) ? newNode : newNodeGrandma;

		// when using patricia trees the children points
		// backwards to grandmas informing if its grandmas
		// has bigger or lowers values, when a node has
		// a child it must "forget" about your own grandmas
		// and points to your new children
		if (newNodeGrandma == newNodeParent.leftChild) {
			newNodeParent.leftChild = newNode;
		} else {
			newNodeParent.rightChild = newNode;
		}

		return node;
	}

	public void delete(int value) {
		this.root = this.delete(this.root.leftChild, this.root, value);
	}

	private PatriciaNode delete(PatriciaNode node, PatriciaNode parentNode, int value) {

		if (node == null) return null;

		String binaryValue = toBinary(value);
		while (parentNode.bitNumber > node.bitNumber) {
			node = parentNode;
			parentNode = isBit1At(binaryValue, parentNode.bitNumber) ? parentNode.rightChild : parentNode.leftChild;
		}

		return this.erase(node, parentNode, null);
	}

	private PatriciaNode erase(PatriciaNode deletedNode, PatriciaNode parentNode, PatriciaNode value) {

		// its a leaf
		if (deletedNode.leftChild == null && deletedNode.rightChild == null) {
			deletedNode = null;
			return deletedNode;
		}

		// its a branch with one child at right
		if (deletedNode.leftChild == null) {
			deletedNode = deletedNode.rightChild;
			return deletedNode;
		}

		// its a branch with one child at left
		if (deletedNode.rightChild == null) {
			deletedNode = deletedNode.leftChild;
			return deletedNode;
		}

		// its a branch with two children
		PatriciaReplacement replacementCandidate = findSuitableReplacementValue(deletedNode);

		// TODO try to finish this shit!!!
		deletedNode.data = replacementCandidate.node.data;
		replacementCandidate.node.leftChild = deletedNode.leftChild;
		replacementCandidate.node.rightChild = deletedNode.rightChild;

		if (parentNode.leftChild == deletedNode) {
			parentNode.leftChild = replacementCandidate.node;
		}

		if (parentNode.rightChild == deletedNode) {
			parentNode.rightChild = replacementCandidate.node;
		}

		return deletedNode;

	}

	private PatriciaReplacement findSuitableReplacementValue(PatriciaNode node) {

		// finding the minimum at right
		int rightDepth = 0;
		int maxRightLeftBitNumber = 0;
		PatriciaNode rightSucessor = node.rightChild;

		while (rightSucessor.bitNumber > maxRightLeftBitNumber) {
			rightDepth++;
			maxRightLeftBitNumber = rightSucessor.bitNumber;

			if (maxRightLeftBitNumber >= rightSucessor.leftChild.bitNumber) break;
			rightSucessor = rightSucessor.leftChild;
		}

		// finding the maximum at left
		int leftDepth = 0;
		int maxLeftRightBitNumber = 0;
		PatriciaNode leftSucessor = node.leftChild;

		while (leftSucessor.bitNumber > maxLeftRightBitNumber) {
			leftDepth++;
			maxLeftRightBitNumber = leftSucessor.bitNumber;

			if (maxLeftRightBitNumber >= leftSucessor.rightChild.bitNumber) break;
			leftSucessor = leftSucessor.rightChild;
		}

		if (rightDepth >= leftDepth) {
			return new PatriciaReplacement(rightSucessor, false, true);
		} else {
			return new PatriciaReplacement(leftSucessor, true, false);
		}
	}

	private String toBinary(int value) {
		String binary = Integer.toString(value, 2);
		while (binary.length() != MaxBits)
			binary = "0" + binary;

		return binary;
	}

	private boolean isBit1At(String binary, int i) {
		if (binary.charAt(i - 1) == '1') return true;

		return false;
	}
}