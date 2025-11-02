package case_study_interfaces;

public class Cat implements Comparable<Cat>
{
	private String name;
	private int age;
	
	public Cat() {
		this.name = "Grumpy";
		this.age = 7;
	}
	
	public Cat(String n) {
		this.name = n;
		this.age = 0;
	}
	
	public Cat(String n, int a) {
		this.name = n;
		this.age = a;
	}
	

	 // ADD CODE BELOW

    public int compareTo(Cat o)
    {
        if (this.age == o.age)
        {
            return this.name.compareTo(o.name);
        }
        else
            return this.age - o.age; // will either be positive or negative
    }

    public boolean equals(Object obj)
    {
        return true;
        /*
        if (obj instanceof Cat)
        {
            Cat otherCat = new Cat();
        }
         */
    }

    // END OF ADDED CODE

    public String toString() {
		return "(" + this.name + ", " + this.age + ")";
	}
	
	public static void main(String[] args) {
		SLinkedList<Cat> myCats = new SLinkedList<Cat>();
		myCats.addFirst(new Cat());
		myCats.addFirst(new Cat("Tiger"));
		myCats.addFirst(new Cat("Spritz", 12));
		myCats.addFirst(new Cat("Kitty", 2));
		myCats.addFirst(new Cat("Ginger", 2));
		
		System.out.println(myCats);
		
	}


}
