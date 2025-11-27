// Naael Mufti, McGill ID: 261279652
package assignment3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Catfeinated implements Iterable<Cat> {
    public CatNode root;

    public Catfeinated()
    {
    }

    public Catfeinated(CatNode dNode)
    {
        this.root = dNode;
    }

    // Constructor that makes a shallow copy of a Catfeinated cafe
    // New CatNode objects, but same Cat objects
    public Catfeinated(Catfeinated cafe)
    {
        this.root = copyNode(cafe.root);
    }

    // helper method to recursively copy the tree
    private CatNode copyNode(CatNode node) {
        if (node == null)
            return null;
        CatNode newNode = new CatNode(node.catEmployee);
        newNode.junior = copyNode(node.junior);
        if (newNode.junior != null)
            newNode.junior.parent = newNode;

        newNode.senior = copyNode(node.senior);

        if (newNode.senior != null)
            newNode.senior.parent = newNode;
        return newNode;
    }


    // add a cat to the cafe database
    public void hire(Cat c) {
        if (root == null)
            root = new CatNode(c);
        else
            root = root.hire(c);
    }

    // removes a specific cat from the cafe database
    public void retire(Cat c) {
        if (root != null)
            root = root.retire(c);
    }

    // get the oldest hire in the cafe
    public Cat findMostSenior()
    {
        if (root == null)
            return null;
        return root.findMostSenior();
    }

    // get the newest hire in the cafe
    public Cat findMostJunior() {
        if (root == null)
            return null;

        return root.findMostJunior();
    }

    // private helper function
    private void add(ArrayList<CatNode> extra, CatNode node) {
        int i = 0;
        while(i < extra.size() && extra.get(i).catEmployee.getFurThickness() > node.catEmployee.getFurThickness() ) i++;
        extra.add(i, node);
    }

    // returns a list of cats containing the top numOfCatsToHonor cats
    // in the cafe with the thickest fur. Cats are sorted in descending
    // order based on their fur thickness.
    public ArrayList<Cat> buildHallOfFame(int numOfCatsToHonor)
    {
        ArrayList<Cat> hallOfFame = new ArrayList<>();
        ArrayList<CatNode> extra = new ArrayList<>(); // This keeps CatNode with thickest fur at index 0
        extra.add(root);
        while(!extra.isEmpty() && hallOfFame.size() < numOfCatsToHonor) {
            CatNode node =  extra.removeFirst();
            hallOfFame.add(node.catEmployee);


            if(node.junior != null) add(extra, node.junior);
            if(node.senior != null) add(extra, node.senior);
        }
        return hallOfFame;
    }

    // Returns the expected grooming cost the cafe has to incur in the next numDays days
    public double budgetGroomingExpenses(int numDays) {
        double groomingExpenses = 0.0;
        for(Cat cur : this)
        {
            if(cur.getDaysToNextGrooming() <= numDays) groomingExpenses += cur.getExpectedGroomingCost();
        }
        return groomingExpenses;
    }

    // returns a list of list of Cats.
    // The cats in the list at index 0 need be groomed in the next week.
    // The cats in the list at index i need to be groomed in i weeks.
    // Cats in each sublist are listed in from most senior to most junior.
    public ArrayList<ArrayList<Cat>> getGroomingSchedule()
    {
        ArrayList<ArrayList<Cat>> schedule = new ArrayList<ArrayList<Cat>>();

        if (root == null)
            return schedule; // edge case

        //get cats in ascending order of seniority
        ArrayList<Cat> allCats = new ArrayList<Cat>();
        Iterator<Cat> iter = this.iterator();
        while(iter.hasNext())
            allCats.add(iter.next());


        // process cats and expand schedule
        for (int i = 0; i < allCats.size(); i++) {
            Cat cat = allCats.get(i);
            int weekIndex = cat.getDaysToNextGrooming() / 7;

            // expand schedule if smaller than weekIndex
            while (schedule.size() <= weekIndex) {
                schedule.add(new ArrayList<>());
            }
            schedule.get(weekIndex).add(cat);
        }

        return schedule;


    }


    public Iterator<Cat> iterator() {
        return new CatfeinatedIterator();
    }


    public static class CatNode {
        public Cat catEmployee;
        public CatNode junior;
        public CatNode senior;
        public CatNode parent;

        public CatNode(Cat c) {
            this.catEmployee = c;
            this.junior = null;
            this.senior = null;
            this.parent = null;
        }

        // add the c to the tree rooted at this and returns the root of the resulting tree
        public CatNode hire(Cat c) {
            // BST insert
            if (c.compareTo(this.catEmployee) < 0) { // junior
                if (this.junior == null) {
                    this.junior = new CatNode(c);
                    this.junior.parent = this;
                } else {
                    this.junior = this.junior.hire(c);
                }


                if (this.junior.catEmployee.getFurThickness() > this.catEmployee.getFurThickness()) // upheap
                {
                    return rightRotate(this);
                } // else more senior
            } else {
                if (this.senior == null) {
                    this.senior = new CatNode(c);
                    this.senior.parent = this;
                } else {
                    this.senior = this.senior.hire(c);
                }

                if (this.senior.catEmployee.getFurThickness() > this.catEmployee.getFurThickness()) //upheap
                {
                    return leftRotate(this);
                }
            }
            return this;
        }


        // Right rotation: senior child becomes parent
        private CatNode rightRotate(CatNode parent) {
            CatNode child = parent.junior;
            parent.junior = child.senior;
            if (child.senior != null) child.senior.parent = parent;
            child.senior = parent;
            child.parent = parent.parent;
            parent.parent = child;
            return child;
        }


        private CatNode leftRotate(CatNode parent) {
            CatNode child = parent.senior;
            parent.senior = child.junior;
            if (child.junior != null) child.junior.parent = parent;
            child.junior = parent;
            child.parent = parent.parent;
            parent.parent = child;
            return child;
        }

        // remove c from the tree rooted at this and returns the root of the resulting tree
        public CatNode retire(Cat c) {
            if (c.equals(this.catEmployee)) {
                // Case 1: leaf or single child
                if (junior == null && senior == null) return null;
                if (junior == null) {
                    senior.parent = parent;
                    return senior;
                }
                if (senior == null) {
                    junior.parent = parent;
                    return junior;
                }


                // Case 2: both children
                CatNode seniorCat = junior.findMostSeniorNode(); // always go right
                this.catEmployee = seniorCat.catEmployee;
                // remove from left subtree
                junior = junior.retire(seniorCat.catEmployee);
            } else if (c.compareTo(this.catEmployee) < 0) { // more junior
                if (junior != null) junior = junior.retire(c);
            } else { // more senior
                if (senior != null) senior = senior.retire(c);
            }

            // now we downheap to fix
            return downheap(this);
        }

        //helper method
        private CatNode findMostSeniorNode()  {
            CatNode mostSeniorNode = this;
            while(mostSeniorNode.senior != null) {
                mostSeniorNode = mostSeniorNode.senior;
            }
            return mostSeniorNode;
        }

        private CatNode downheap(CatNode node) {
            if (node == null) return null;

            CatNode maxFurChild = null;

            if (node.junior != null && node.senior != null)
            {
                if (node.junior.catEmployee.getFurThickness() >= node.senior.catEmployee.getFurThickness())
                {
                    maxFurChild = node.junior;
                } else
                {
                    maxFurChild = node.senior;
                }
            } else if (node.junior != null)
            {
                maxFurChild = node.junior;
            } else if (node.senior != null)
            {
                maxFurChild = node.senior;
            } else
            {
                return node;
            }

            if (maxFurChild.catEmployee.getFurThickness() > node.catEmployee.getFurThickness())
            {
                if (maxFurChild == node.junior) return rightRotate(node); // rotate
                else return leftRotate(node);
            }

            return node;
        }


        // find the cat with highest seniority in the tree rooted at this
        public Cat findMostSenior() {
            return findMostSeniorNode().catEmployee; // can use our helper method
        }

        // find the cat with lowest seniority in the tree rooted at this
        public Cat findMostJunior() { // go left until we can't anymore
            CatNode mostJuniorNode = this;
            while(mostJuniorNode.junior != null)
                mostJuniorNode = mostJuniorNode.junior;
            return mostJuniorNode.catEmployee;
        }


        // Feel free to modify the toString() method if you'd like to see something else displayed.
        public String toString() {
            String result = this.catEmployee.toString() + "\n";
            if (this.junior != null) {
                result += "junior than " + this.catEmployee.toString() + " :\n";
                result += this.junior.toString();
            }
            if (this.senior != null) {
                result += "senior than " + this.catEmployee.toString() + " :\n";
                result += this.senior.toString();
            } /*
			if (this.parent != null) {
				result += "parent of " + this.catEmployee.toString() + " :\n";
				result += this.parent.catEmployee.toString() +"\n";
			}*/
            return result;
        }
    }


    public class CatfeinatedIterator implements Iterator<Cat> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
        private ArrayList<Cat> cats;
        private int index;

        public CatfeinatedIterator() {
            cats = new ArrayList<Cat>();
            index = 0;

            if (root != null)
            {
                inOrderTraversal(root);
            }
        }

        // helper method to traverse
        private void inOrderTraversal(CatNode node) {
            if (node == null)
            {
                return;
            }

            // Visit senior subtree first
            inOrderTraversal(node.senior);
            // Visit current node
            cats.add(node.catEmployee);
            // Visit junior subtree last
            inOrderTraversal(node.junior);
        }

        public Cat next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException("No such Element");
            }
            int curIndex = index;
            index++;
            return cats.get(curIndex);
        }

        public boolean hasNext() {
            return index < cats.size();
        }

    }

    public static void main(String[] args) {
        Cat B = new Cat("Buttercup", 45, 53, 5, 85.0);
        Cat C = new Cat("Chessur", 8, 23, 2, 250.0);
        Cat J = new Cat("Jonesy", 0, 21, 12, 30.0);
        Cat JJ = new Cat("JIJI", 156, 17, 1, 30.0);
        Cat JTO = new Cat("J. Thomas O'Malley", 21, 10, 9, 20.0);
        Cat MrB = new Cat("Mr. Bigglesworth", 71, 0, 31, 55.0);
        Cat MrsN = new Cat("Mrs. Norris", 100, 68, 15, 115.0);
        Cat T = new Cat("Toulouse", 180, 37, 14, 25.0);
        Cat BC = new Cat("Blofeld's cat", 6, 72, 18, 120.0);
        Cat L = new Cat("Lucifer", 10, 44, 20, 50.0);

        Catfeinated cafe = new Catfeinated();
        cafe.hire(B);
        cafe.hire(C);
        cafe.hire(J);
        cafe.hire(JJ);
        cafe.hire(JTO);
        cafe.hire(MrB);
        cafe.hire(MrsN);
        cafe.hire(T);
        cafe.hire(BC);
        cafe.hire(L);
        System.out.println(cafe.root);

    }


}


