public class Midterm1
{

    public static boolean isCorrect(double studentAnswer)
    {
        double correctAnswer = Math.sqrt(1.0/2.0);
        return studentAnswer == correctAnswer;
    }

    public static void main(String[] args)
    {

        System.out.println(isCorrect(0.707));
        double a = 2.0;
        double b = 2.00;
        if(a == b)
        {
            System.out.println("true");
        }else
        {
            System.out.println("false");
        }


        int x = 5;
        System.out.println(2*x++);
        System.out.println(x);

    }
}
