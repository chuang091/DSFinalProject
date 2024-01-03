import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class Main {

/*
    public static void main(String[] args) throws IOException {

        //取得使用者輸入.
        Scanner sc = new Scanner(System.in);
        System.out.println("0: Single Thread; 1: Multi-Thread");
        int process = sc.nextInt();
        System.out.print("請輸入一個詞： ");
        String input = sc.next();
  

        //設定keywords的權重
        KeywordList keywordList = new KeywordList();
        keywordList.add(new Keyword("恐怖", 5.0f));
        keywordList.add(new Keyword("科幻", 5.0f));
        keywordList.add(new Keyword("毛骨悚然", 5.0f));
        keywordList.add(new Keyword("超能力", 5.0f));
        keywordList.add(new Keyword("神鬼", 5.0f));
        keywordList.add(new Keyword("未來", 5.0f));
        keywordList.add(new Keyword("驚悚", 4.5f));
        keywordList.add(new Keyword("太空", 4.5f));
        keywordList.add(new Keyword("靈異", 4.5f));
        keywordList.add(new Keyword("外星", 4.5f));
        keywordList.add(new Keyword("詭異", 4.0f));
        keywordList.add(new Keyword("機器人", 4.0f));
        keywordList.add(new Keyword("血腥", 4.0f));
        keywordList.add(new Keyword("時空", 4.0f));
        keywordList.add(new Keyword("咒怨", 4.0f));
        keywordList.add(new Keyword("星際", 4.0f));
        keywordList.add(new Keyword("鬼屋", 3.5f));
        keywordList.add(new Keyword("末日", 3.5f));
        keywordList.add(new Keyword("暗黑", 3.5f));
        keywordList.add(new Keyword("人工智能", 3.5f));
        keywordList.add(new Keyword("屍體", 3.0f));
        keywordList.add(new Keyword("穿越", 3.0f));
        keywordList.add(new Keyword("詭秘", 2.0f));
        keywordList.add(new Keyword("幻想", 2.0f));
     

        //取得標題和URL，計算分數
        long start = System.currentTimeMillis();
        HashMap<String, String> titleAndURL = new GoogleQuery(input).query();
        ArrayList<WebTree> webTrees = new ArrayList<WebTree>();
        
        //System.out.println(titleAndURL.size());
        int[] progress = new int[1];
        
        
        for (Map.Entry<String, String> entry : titleAndURL.entrySet()) {
        	//System.out.println(entry.getKey());
        	
        	if (process == 0) {
        		String title = entry.getKey();
                String url = entry.getValue();
                
                try {
                	 WebPage webPage = new WebPage(url, title);
		             WebTree webTree = new WebTree(webPage);

		             // 網頁的分數
		             webTree.setNodeScore(keywordList.getList());
		             webTrees.add(webTree);
                } catch (IOException e) {
                    // URL 找不到的情況
                    //System.out.println("Some URL is Fucked");
                }
                
                progress[0] += 1;
                System.out.println(String.format("Progress: %d", Math.round(progress[0]/(double) titleAndURL.size() * 100)) + "%");
        	} else {
        		class Job implements Runnable {
        			int[] progress;
        			
        			@SuppressWarnings("unused")
					public Job() {
        				
        			}
        			
        			public Job(int progress[]) {
        				this.progress = progress;
        			}

					@Override
					public void run() {
						synchronized(this) {
							method(progress);
						}
					}
					
					private void method(int[] progress) {
						String title = entry.getKey();
    		            String url = entry.getValue();

    		            try {
    		                WebPage webPage = new WebPage(url, title);
    		                WebTree webTree = new WebTree(webPage);

    		                // 網頁的分數
    		                webTree.setNodeScore(keywordList.getList());
    		                webTrees.add(webTree);
    		            } catch (IOException e) {
    		                // URL 找不到的情況
    		                
    		            }
    		            
    		            progress[0] += 1;
    		            System.out.println(String.format("Progress: %d", Math.round(progress[0]/(double) titleAndURL.size() * 100)) + "%");
					}
        		}
        		
        		Thread thread = new Thread(new Job(progress));
            	
            	thread.start();
        	}
        }
        // 使用快速排序將 webPages 由分數高到低排序
        
        while (progress[0] < titleAndURL.entrySet().size()) {
        	try {
        		TimeUnit.MILLISECONDS.sleep(100);
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
        
        System.out.print("Total Pages: ");
        System.out.println(webTrees.size());
        //WebPageSorter.quickSort(webTrees);
       
            
        

       //印出結果
        for (WebTree webTree : webTrees) {
            System.out.println("Title：" + webTree.getRoot().getWebPage().getName());
            System.out.println("URL：" + webTree.getRoot().getWebPage().getUrl());
            System.out.println("Points：" + webTree.getRoot().getWebPage().getScore());

            

            System.out.println("-------------------------------------------------------");
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println(String.format("Total Time Taken: %.2f seconds", (end - start) / 1000f));
        sc.close();
    }
    */
	
	public static void main(String[] args) {
		Algorithm algorithm = new Algorithm();
		try {
			String result = algorithm.getResult("鬼片");
			System.out.println(result);
			//System.out.println(result);
			//Return the result like this: return result or :return algorithm.getResult("Query");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    //Total Time Taken: 102.82 seconds (科幻片) (35有效網站)
    //Total Time Taken: 23.07 seconds (科幻片) (40有效網站)
    //Total Time Taken: 102.06 seconds (鬼片) (37有效網站)
    //Total Time Taken: 11.18 seconds (鬼片) (37有效網站)
}

	
