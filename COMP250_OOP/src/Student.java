import java.util.Arrays;

public class Student
{
    private String name;
    private int studentID;
    private String [] courses;

    public Student()
    {
        System.out.println("Creating a new object");
    }

    public Student(String name, int ID, String[] c)
    {
        this.name = name; // "this" only necessary here because both variables same name
        this.studentID = ID;

        /*for(int i=0; i<this.courses.length; i++)
        {
            this.courses[i] = c[i];
        }
        works if array size known
         */
        this.courses = c; // both points to same object so both will be impacted with a change
    }

    public static void main(String[] args)
    {
        Student x = new Student();
        System.out.println(x); // prints the reference
        String[] listCourses = {"COMP202", "COMP250"};
        Student s2 = new Student("Leila",12134567, listCourses);

        x.printName();
        s2.printName();
        System.out.println(s2);

        System.out.println(Arrays.toString(s2.courses));
        listCourses[0] = "Comp204";
        System.out.println(Arrays.toString(s2.courses)); // array of strings is mutable
    }

    public void printName() // only works within class
    {
        System.out.println(this.name);
    }

    public String getName() // outside of the class can use this instead
    {
        return this.name;
    }

    public String toString()
    {
        return "Name: " + this.name + "ID: " + this.studentID;
    }

    public void setStudentID(int newID)
    {
        if (newID > 0)
            this.studentID = newID;
        else
            throw new IllegalArgumentException("Student ID must be a positive integer");
    }

    public void setName(String newName)
    {
        this.name = newName;
    }


    //addCourse(String )
}
