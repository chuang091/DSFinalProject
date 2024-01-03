import java.util.ArrayList;
import java.util.Collections;

public class WebPageSorter {

    // §Ö³t±Æ§Ç.
    public static void quickSort(ArrayList<WebTree> webTrees) {
        quickSort(webTrees, 0, webTrees.size() - 1);
    }

    private static void quickSort(ArrayList<WebTree> webTrees, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(webTrees, low, high);
            quickSort(webTrees, low, pivotIndex - 1);
            quickSort(webTrees, pivotIndex + 1, high);
        }
    }

    private static int partition(ArrayList<WebTree> webTrees, int low, int high) {
        double pivot = webTrees.get(high).getTreeScore();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (webTrees.get(j).getTreeScore() >= pivot) {
                i++;
                Collections.swap(webTrees, i, j);
            }
        }

        Collections.swap(webTrees, i + 1, high);
        return i + 1;
    }
}
