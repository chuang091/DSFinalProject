import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Algorithm {
	int process = 0;//������(�ثe�i�H��ʤ���)
	KeywordList keywordList;
	String query;
	
	public Algorithm() {
		keywordList = new KeywordList();
        keywordList.add(new Keyword("����", 5.0f));
        keywordList.add(new Keyword("���", 5.0f));
        keywordList.add(new Keyword("�򰩮��M", 5.0f));
        keywordList.add(new Keyword("�W��O", 5.0f));
        keywordList.add(new Keyword("����", 5.0f));
        keywordList.add(new Keyword("����", 5.0f));
        keywordList.add(new Keyword("�宪", 4.5f));
        keywordList.add(new Keyword("�Ӫ�", 4.5f));
        keywordList.add(new Keyword("�F��", 4.5f));
        keywordList.add(new Keyword("�~�P", 4.5f));
        keywordList.add(new Keyword("�޲�", 4.0f));
        keywordList.add(new Keyword("�����H", 4.0f));
        keywordList.add(new Keyword("��{", 4.0f));
        keywordList.add(new Keyword("�ɪ�", 4.0f));
        keywordList.add(new Keyword("�G��", 4.0f));
        keywordList.add(new Keyword("�P��", 4.0f));
        keywordList.add(new Keyword("����", 3.5f));
        keywordList.add(new Keyword("����", 3.5f));
        keywordList.add(new Keyword("�t��", 3.5f));
        keywordList.add(new Keyword("�H�u����", 3.5f));
        keywordList.add(new Keyword("����", 3.0f));
        keywordList.add(new Keyword("��V", 3.0f));
        keywordList.add(new Keyword("�ޯ�", 2.0f));
        keywordList.add(new Keyword("�۷Q", 2.0f));
        
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
        //WebPageSorter.quickSort(webTrees);
       
            
        
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
