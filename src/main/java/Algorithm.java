import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Algorithm {
	int process = 1;//������(�ثe�i�H��ʤ���)
	KeywordList keywordList;
	String query;
	
	public Algorithm() {
		keywordList = new KeywordList();
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
        
        query = "";
	}
	
	public String getResult(String query) throws IOException {
		this.query = query;
		
		String result = start();
		return result;
	}
	
	public String start() throws IOException {
		//���o���D�MURL�A�p�����
        long start = System.currentTimeMillis();
        HashMap<String, String> titleAndURL = new GoogleQuery(query).query();
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

		             // ����������
		             webTree.setNodeScore(keywordList.getList());
		             webTrees.add(webTree);
                } catch (IOException e) {
                    // URL �䤣�쪺���p
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

    		                // ����������
    		                webTree.setNodeScore(keywordList.getList());
    		                webTrees.add(webTree);
    		            } catch (IOException e) {
    		                // URL �䤣�쪺���p
    		                
    		            }
    		            
    		            progress[0] += 1;
    		            System.out.println(String.format("Progress: %d", Math.round(progress[0]/(double) titleAndURL.size() * 100)) + "%");
					}
        		}
        		
        		Thread thread = new Thread(new Job(progress));
            	
            	thread.start();
        	}
        }
        // �ϥΧֳt�ƧǱN webPages �Ѥ��ư���C�Ƨ�
        
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
        WebPageSorter.quickSort(webTrees);
       
            
        
        String result = "";
       //�L�X���G
        for (WebTree webTree : webTrees) {
            System.out.println("Title�G" + webTree.getRoot().getWebPage().getName());
            System.out.println("URL�G" + webTree.getRoot().getWebPage().getUrl());
            System.out.println("Points�G" + webTree.getRoot().getWebPage().getScore());

            

            System.out.println("-------------------------------------------------------");
            
            result += String.format("%s, %s, %s\n", webTree.getRoot().getWebPage().getName(), webTree.getRoot().getWebPage().getUrl(), webTree.getRoot().getWebPage().getScore());
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println(String.format("Total Time Taken: %.2f seconds", (end - start) / 1000f));
        
        return result;
	}
}
