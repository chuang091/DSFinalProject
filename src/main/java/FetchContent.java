import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.ArrayList;

public class FetchContent {
	private final String url = "https://www.google.com/search";
	private String keyword;
	private int num;
	
	public FetchContent(String keyword, int num) {
		this.keyword = keyword;
		this.num = num;
	}
	
	//Please don't remove this constructor as it is needed for overloading
	public FetchContent() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> fetchContent() throws IOException {
		ArrayList<String> searchResults = new ArrayList<String>();
		
		String searchUrl = String.format("%s?q=%s&num=%d", url, keyword, num);
		Document doc = Jsoup.connect(searchUrl).userAgent("Steve/5.1").get();
		
		Elements results = doc.select("div > a");
		
		if (results.size() != 0) {
			for (Element result: results) {
				String linkHref = result.attr("href");
				
				if (linkHref.substring(0, 7).equals("/url?q=")) {
					int stop = linkHref.indexOf("&sa=");
					
					if (stop != -1) {
						searchResults.add(linkHref.toString().substring(7, stop).replace("%25", "%"));
					} else {
						searchResults.add(linkHref.toString().substring(7));
					}
				}
			}
		} else {
			System.out.println("Empty");
		}
		
		return searchResults;
		
		//https://accounts.google.com/ServiceLogin%3Fcontinue%3Dhttps://www.google.com/search%253Fq%253Dcomputer%2526num%253D5%26hl%3Dzh-TW&opi=89978449&sa=U&ved=0ahUKEwjdoOyW_buCAxXX4mEKHSeFDVMQxs8CCGU&usg=AOvVaw1LK1M9Kwv80FWlp0H49eD5

	}
	
	public void setParameter(String input, int num) {
		this.keyword = input;
		this.num = num;
	}
}
