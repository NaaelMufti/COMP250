public class Car
{
    // Define the attributes/fields of the class Car
    public String brand;
    public String color;
    public int year;
    public double speed;
    private static int carCount; // static because it will be same value with all cars

    public Car (String brand, int year) // constructor
    {
        // local variables
        this.brand = brand; // change the attribute brand belonging to class
        this.year = year; // means that when initialize new Car, need to specify the variables here
       // this.speed = 0; // every car will have speed of 0
    }

    /* Alternative method (not as good)
    public Car (String b, int y)
    {
        brand = b;
        year = y;
        speed = 0;
    }
     */

    public Car()  // overloading
    {
        System.out.println("Creating empty car");
    }

    public void honk()
    {
        System.out.println("Beep Beep " + this.brand); // this will specify for which instance
    }

    public static void main(String[] args)
    {
        Car myCar = new Car("BMW", 2024);// new creates a reference type (not primitive)
        Car myCar2 = new Car("Audi", 2019);
        Car myCar3 = new Car();

        myCar.honk();
        myCar2.honk();

        // don't need the following because of constructor
        /*
        myCar.brand = "BMW";
        myCar.year = 2024;
         */

        /*
        System.out.println(myCar.brand); // objectName.attribute
        System.out.println(myCar.year);

         */
    }
}
