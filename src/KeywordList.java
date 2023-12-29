import java.util.ArrayList;

public class KeywordList {
    private ArrayList<Keyword> lst;

    public KeywordList() {
        this.lst = new ArrayList<Keyword>();
    }

    public void add(Keyword keyword) {
        lst.add(keyword);
    }

    public ArrayList<Keyword> getList() {
        return lst;
    }

    public void find(String s)
    {
        int maxValue = -1;
        Keyword LCS = null;

        for (Keyword k : lst)
        {
            int lcs = findLCS(k.name, s);

            if (lcs > maxValue)
            {
                maxValue = lcs;
                LCS = k;
            }
        }
        System.out.println(s + ": " + LCS.toString());
    }

    // 1. Implement the LCS algorithm.
    // Return the length of lcs
    public int findLCS(String x, String y)
    {
        int m = x.length();
        int n = y.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else if (x.charAt(i - 1) == y.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        // Uncomment the line below to print the DP matrix
        // printMatrix(dp);

        return dp[m][n];
    }

    
}
