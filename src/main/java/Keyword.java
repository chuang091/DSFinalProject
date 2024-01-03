
public class Keyword {
	private String name;
	private int count;
	private float weight;
	
	public Keyword(String name, float weight) {
		this.name = name;
		this.weight = weight;
		count = 0;
	}
	
	public String toString() {
		return String.format("[%s,%d,%f]", name, count, weight);
	}
	
	public String getName() {
		return name;
	}
	
	public int getCount() {
		return count;
	}
	
	public float getWeight() {
		return weight;
	}
}
