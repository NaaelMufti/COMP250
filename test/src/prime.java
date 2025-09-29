public class prime
{
    public static void main(String[] args)
    {
        System.out.println(isPrime(5));
    }

    public static boolean isPrime(int n)
    {
        if (n < 2) return false;

        int d = 2;
        while (d < n)
        {
            if ( n % d == 0)
                return false;
            d++;
        }

        return true;
    }
}
