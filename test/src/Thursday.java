public class Thursday
{
    public static void main(String[] args)
    {
        int[] a1 = {};
        int[] a2 = {1,2,3};

        System.out.println(getIntersectionSize(a1, a2));
    }

    public static int getIntersectionSize(int[] a, int[] b)
    {
        int count = 0;
        for(int i = 0; i < a.length; i++)
        {
            for(int j = 0; j < b.length;j++)
            {
                if(a[i] == b[j])
                {
                    count++;
                }
            }
        }

        return count;
        /*
        int size = 0;

        int indexA = 0;
        int indexB = 0;

        while (indexA < a.length && indexB < b.length)
        {
            if (a[indexA] == b[indexB])
            {
                size++;
                indexA++;
                indexB++;
            } else if (a[indexA] < b[indexB])
            }
        }
         */
    }
}
