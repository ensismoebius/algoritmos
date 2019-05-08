package patricia;

public class mainClass {

	public static void main(String[] args) {

		Patricia<Integer> p = new Patricia<Integer>();
		p.addNode("amor", 1);
		p.addNode("amoroso", 2);
		p.addNode("amar", 3);
		p.addNode("amazonia", 4);
		p.addNode("avante", 5);
		p.addNode("avalanche", 6);
		p.addNode("avalanches", 7);
		p.addNode("a", 8);
		p.addNode("casa", 9);
		p.addNode("casou", 10);
		p.addNode("fez", 11);
		p.addNode("Quiabo", 12);
		p.addNode("Cuica", 13);
		p.addNode("Caiu", 14);
		p.addNode("faria", 15);
		p.addNode("fui", 16);
		p.addNode("zuado", 17);
		p.addNode("war", 18);
		p.addNode("wakanda", 19);

		try {
			p.delNode("war");
			System.out.println(p.getValue("war"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
