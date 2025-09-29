public class TestStudent
{
    public static void main(String[] args)
    {
        Student s1 = new Student();
        // s1.name = "Bob"; won't work because name is private, instead use getters and setters
        s1.setName("Bob");
        System.out.println(s1.getName()); // can't do s1.name because name set to private
    }
}
