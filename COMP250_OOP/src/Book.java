public class Book
{
    public String title; // if static all instances have same title
    public String author;

    public Book()
    {
    }

    public Book(String title, String author)
    {
        this.title = title;
        this.author = author;
    }

    public String toString()
    {
        return this.title + " By: " + this.author;
    }
}

