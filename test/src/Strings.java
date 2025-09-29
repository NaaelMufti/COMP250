public class Strings
{
    public static void main(String[] args)
    {
        String s = "word";
        /* the following is also an alternative if want to assign new variable
        String a = replace(s);
        System.out.println(a);
         */
        System.out.println(replace(s));
    }

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
}
