package patricia;

public class PNode<Type> {

	public int compareAt;
	
	public String key;
	public Type value;

	public PNode<Type> left;
	public PNode<Type> right;

	public PNode() {
		this.compareAt = -1;
		this.left = null;
		this.right = null;
	}
}
