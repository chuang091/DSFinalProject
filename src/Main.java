import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        //取得使用者輸入
        Scanner sc = new Scanner(System.in);
        System.out.print("請輸入一個詞： ");
        String inputText = sc.nextLine();
  

        //設定keywords的權重
        KeywordList keywordList = new KeywordList();
        keywordList.add(new Keyword("恐怖片", 5.0f));
        keywordList.add(new Keyword("科幻片", 5.0f));
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
     

        //取得標題和URL，計算所有分數
        HashMap<String, String> titleAndURL = new GoogleQuery(inputText).query();
        ArrayList<WebPage> webPages = new ArrayList<>();

        for (Map.Entry<String, String> entry : titleAndURL.entrySet()) {
            String title = entry.getKey();
            String url = entry.getValue();

            try {
                WebPage webPage = new WebPage(url, title);

                // 網頁的分數
                webPage.setScore(keywordList.getList());
                webPages.add(webPage);
            } catch (IOException e) {
                // 處理 URL 找不到的情況
                
            }
        }
        // 使用快速排序將 webPages 由分數高到低排序
        WebPageSorter.quickSort(webPages);
       
            
        

       //印出結果
        for (WebPage webPage : webPages) {
            System.out.println("Title：" + webPage.name);
            System.out.println("URL：" + webPage.url);
            System.out.println("Points：" + webPage.score);

            

            System.out.println("-------------------------------------------------------");
        }
        sc.close();
    }
}
