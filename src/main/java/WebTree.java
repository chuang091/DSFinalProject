import java.io.IOException;
import java.util.ArrayList;

public class WebTree implements Comparable<WebTree>{
	private WebNode root;
	
	public WebTree(WebPage root) {
		this.root = new WebNode(root, null);
		
		//root.getChildren();
	}
	
	public WebTree(WebNode root) {
		this.root = root;
	}
	
	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		root.setNodeScore(keywords);
	}
	
	public double getTreeScore() {
		return root.getNodeScore();
	}
	
	public WebNode getRoot() {
		return root;
	}
	
	@Override
	public int compareTo(WebTree other) {
        // 在這裡實現比較邏輯，例如按照分數降序排列.
        return Double.compare(other.root.getNodeScore(), this.root.getNodeScore());
    }
}
