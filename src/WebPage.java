import java.io.IOException;
import java.util.ArrayList;

public class WebPage implements Comparable<WebPage> {

    public String url, name;
    public WordCounter counter;
    public double score;
    
    public double getScore() {
        return this.score;
    }

    public WebPage(String url, String name) {
        this.url = url;
        this.name = name;
        this.counter = new WordCounter(url);
    }

    public void setScore(ArrayList<Keyword> keywords) throws IOException {
        score = 0;
        for (Keyword keyword : keywords) {
            score += keyword.weight * counter.countKeyword(keyword.name);
        }
    }

    @Override
    public int compareTo(WebPage other) {
        // 在這裡實現比較邏輯，例如按照分數降序排列.
        return Double.compare(other.score, this.score);
    }
}
