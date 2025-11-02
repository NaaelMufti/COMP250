import java.util.Arrays;

public class Strings
{


    public static String replace(String t)
    {
        String v = "";
        for (int i = 0; i < t.length(); i++)
        {
            if(t.charAt(i)== 'o')
            {
                v = v + "a";
            } else {
                v = v + t.charAt(i);
            }
        }
        return v;
    }

    public static int[] getFrequency(String s)
    {
        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++)
        {
            freq[s.charAt(i)-'a']++; //
        }

        return freq;
    }

    public static void mystery(int[][] x, int[] y)
    {
        x[0] = new int[2];
        y[0] = x[1][0];
        x[0][1] = y[1];
        x[1] = y;
    }

    public static void main(String[] args)
    {
        String s = "pumpkinpie";
        /* the following is also an alternative if want to assign new variable
        String a = replace(s);
        System.out.println(a);
         */
        //System.out.println(replace(s));

        int[] frequency = Strings.getFrequency(s);
        System.out.println(frequency[4]);
        System.out.println(frequency[8]);
        System.out.println(frequency[10]);
        System.out.println("break");

        int[][] a = {{1,2,3},{4,5}};
        mystery(a, a[0]);
        System.out.println(Arrays.deepToString(a));

        System.out.println("break");

        String[] names = new String[2];
        names[1] = "Will";
        String c = names[1];
        names[0] = c;
        c = "Giulia";
        String b = "Emma";
        names[1] = b;
        b = "Will";

        System.out.println(Arrays.deepToString(names));


    }
}
