package trees.patricia;

public class PatriciaReplacement {

	public boolean comesFromleft;
	public boolean comesFromRight;
	public PatriciaNode node;

	public PatriciaReplacement(PatriciaNode sucessor, boolean left, boolean righ) {
		this.comesFromleft = left;
		this.comesFromRight = righ;
		this.node = sucessor;
	}

}
