public class TestBook
{
    public static void main(String[] args)
    {
        Book b = new Book();
        b.title = "Matilda";
        b.author = "Roald Dahl";
        Book b2 = new Book("Flowers for Algernon", "Daniel Keyes");

        System.out.println(b); // prints the reference (memory address) unless toString is made
        System.out.println(b2.author);
    }
}
