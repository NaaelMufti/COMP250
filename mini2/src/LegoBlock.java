public class LegoBlock {

    public static int countWays(int n)
    {
        if (n == 0) return 1;     // one way (no blocks)
        if (n < 0) return 0;      // no valid way

        return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
    }

    // Private helper method (uses the memo array)
    private static int countWaysMemo(int n, int[] memo) {
        if (n == 0) return 1;
        if (n < 0) return 0;

        if (memo[n] != -1) return memo[n];

        memo[n] = countWaysMemo(n - 1, memo)
                + countWaysMemo(n - 2, memo)
                + countWaysMemo(n - 3, memo);

        return memo[n];
    }

    public static int countWaysMemo(int n)
    {
        int[] memo = new int[n + 1];
        for (int i = 0; i <= n; i++)
        {
            memo[i] = -1;
        }
        return countWaysMemo(n, memo);
    }


    public static void main(String[] args) {
        int n = 4;
        System.out.println("Ways to build " + n + " cm tower: " + countWays(n));
        int b = 5;
        System.out.println("Ways to build: " + b + " cm tower: " + countWaysMemo(b));
    }
}
