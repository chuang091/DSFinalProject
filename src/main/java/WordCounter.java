import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordCounter {
	private String url;
	private String content;
	
	public WordCounter(String url) throws IOException {
		try {
			if (!(url.contains("http://") || url.contains("https://"))) {
				url = "https://" + url;
			}
			
			this.url = URLDecoder.decode(url, "utf-8");
			this.content = fetchContent();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unsupported URL");
		}
		
	}
	
	public ArrayList<WebNode> getChildren(WebNode parent) {
		HashMap<String, String> resultList = new HashMap<String, String>();
		ArrayList<WebNode> children = new ArrayList<WebNode>();
		
		Document doc = Jsoup.parse(content);
		
		Elements lis = doc.select("a");
		//lis = lis.select(".kCrYT");
		
		for (Element li: lis) {
			String url = li.attr("href").replace("/url?q=", "");
			String title = li.select(".vvjwJb").text();
			
			//if (title.equals("")) {
				//continue;
			//}
			if (title == null || title == "") {
				title = "Test" + lis.indexOf(li);
			}
			
			//System.out.println(title + " " + url);
			
			resultList.put(title, url);
			//System.out.println(url);
		}
		
		//System.out.println(resultList.size());
		
		int[] count = new int[1];
		int[] i = {0};
		
		//System.out.println("Next Phase");
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized(this) {
					for (Map.Entry<String, String> entry: resultList.entrySet()) {
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								synchronized(this) {
									try {
										WebPage webPage = new WebPage(entry.getValue(), entry.getKey());
										WebNode child = new WebNode(webPage, parent);
										children.add(child);
										//System.out.println(entry.getValue());
									} catch (IOException e1) {
										//System.out.println("Some WebSites Cannot Be Reached");
										System.out.print("");
										//count[0]++;
										//return;
									} catch (IllegalArgumentException e2) {
										System.out.print("");
									} catch (ClassCastException e3) {
										System.out.print("");
									} catch (Exception e4) {
										System.out.print("");
									}
									i[0]--;
									//System.out.println(i[0] + "Thread");
									
									count[0] ++;
									//System.out.println(count[0]);
								}
							}
						});
						thread.start();
						i[0]++;
					}
					
					while (count[0] < ((double)resultList.size() * 0.95)) {
						try {
						//System.out.println(count[0] + ":" + resultList.size());
							TimeUnit.MILLISECONDS.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		thread.start();
		
		while (i[0] > 0) {
			try {
				//System.out.println(count[0] + ":" + resultList.size());
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return children;
	}
	
	public int countKeyword(String keyword) throws IOException {
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
		
		int result = 0, startIndex = 0, found = -1;
		
		while ((found = content.indexOf(keyword, startIndex)) != -1) {
			result ++;
			startIndex = found + keyword.length();
		}
		
		return result;
	}
	
	private String fetchContent() throws IOException, ClassCastException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(10000);
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream is = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		
		String result = "", line = null;
		while ((line = br.readLine()) != null) {
			result += (String.format("%s\n", line));
		}
		
		return result;
	}
}
