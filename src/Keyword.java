public class Keyword {
    public String name;
    public int count;
    public float weight;

    public Keyword(String name, float weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[" + name + "," + count + "," + weight + "]";
    }
}