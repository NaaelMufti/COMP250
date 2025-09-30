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
    }
}
