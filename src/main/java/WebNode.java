import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	private WebNode parent;
	private ArrayList<WebNode> children;
	private WebPage webPage;
	private double nodeScore;
	
	public WebNode(WebPage webPage, WebNode parent) {
		this.webPage = webPage;
		this.parent = parent;
		children = new ArrayList<WebNode>();
		
		if ((parent == null)) {
			//webPage.getChildren();
			children.addAll(webPage.getChildren(this));
		}
	}
	
	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		//System.out.println(children.size());
		
		for (WebNode child: children) {
			child.setNodeScore(keywords);
			
			nodeScore += child.nodeScore;//Might be changed
		}
		
		webPage.setScore(keywords);
		nodeScore += webPage.getScore();
		//System.out.printf("Name: %s Score: %.2f\n", webPage.getName(), nodeScore);
	}
	
	public void addChild(WebNode child) {
		children.add(child);
		child.parent = this;
	}
	
	public ArrayList<WebNode> getChildren() {
		return children;
	}
	
	public double getNodeScore() {
		return nodeScore;
	}
	
	public WebNode getParent() {
		return parent;
	}
	
	public void setParent(WebNode parent) {
		this.parent = parent;
	}
	
	public WebPage getWebPage() {
		return webPage;
	}
}
