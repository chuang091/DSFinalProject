import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	private String url, name;
	private WordCounter counter;
	private double score;
	
	public WebPage(String url, String name) throws IOException {
		this.url = url;
		this.name = name;
		counter = new WordCounter(url);
		score = 0;
	}
	
	public void setScore(ArrayList<Keyword> keywords) throws IOException {
		for (Keyword keyword: keywords) {
			score += keyword.getWeight() * counter.countKeyword(keyword.getName());
		}
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getName() {
		return name;
	}
	
	public double getScore() {
		return score;
	}
	
	public ArrayList<WebNode> getChildren(WebNode parent) { 
		return counter.getChildren(parent);
	}
}