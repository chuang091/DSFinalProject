import java.util.ArrayList;
import java.util.Collections;

public class WebPageSorter {

    // §Ö³t±Æ§Ç
    public static void quickSort(ArrayList<WebPage> webPages) {
        quickSort(webPages, 0, webPages.size() - 1);
    }

    private static void quickSort(ArrayList<WebPage> webPages, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(webPages, low, high);
            quickSort(webPages, low, pivotIndex - 1);
            quickSort(webPages, pivotIndex + 1, high);
        }
    }

    private static int partition(ArrayList<WebPage> webPages, int low, int high) {
        double pivot = webPages.get(high).getScore();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (webPages.get(j).getScore() >= pivot) {
                i++;
                Collections.swap(webPages, i, j);
            }
        }

        Collections.swap(webPages, i + 1, high);
        return i + 1;
    }
}
