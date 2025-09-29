public class Tuesday
{
    public static void main(String[] args)
    {
        System.out.println(charRightShift('g', 2));   // i
        System.out.println(charRightShift('#', 2));   // #
        System.out.println(charRightShift('z', 27));  // a
        System.out.println(charRightShift('a', -1));  // z
    }

    public static char charRightShift(char c, int n)
    {
        // lower case letters ASCII (a - z)-> 97 - 122
        // upper case letters ASCII (A - Z)-> 65 - 90

        if (c >= 'a' && c <= 'z') // check if lower case
        {
            int pos = c - 'a';
            int newPos = (pos + n);

            return (char)('a' + newPos);
        }

        return c;
    }
}
