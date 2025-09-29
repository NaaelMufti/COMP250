import java.util.Arrays;;

public class ArrayTest
{
    public static void main(String [] args)
    {
        //int [][] a = {{1, 2, 3}, {4, 5}};
        // mystery (a, a[0]);
        //System.out. println (Arrays. deepToString (a));

        int[] x = {2,5,0};
        System.out.println(findMax(x));;
    }

    public static int findMax(int[] a) // used to be psVOID findMax
    {
        //input validation
        if (a.length == 0)
            throw new IllegalArgumentException(); // only needs if say max = a[0]

        int max = a[0]; // used to be 0, but if neg number won't work
        // int max = Integer.MIN_VALUE; makes smallest possible value
        for(int i=0; i<a.length-1;i++) // used to be no -1, but then will go over length by 1
        {
            if (a[i]> max)
            {
                max = a[i];
            }
        }
        return max;
    }
    public static void mystery (int [][] x, int [] y)
    {
        x[0] = new int [2];
        y[0] = x [1][0];
        x[0][1] = y[1];
        x[1] = y;
    }
}
