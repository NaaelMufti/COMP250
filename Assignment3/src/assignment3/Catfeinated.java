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
        if (cafe.root == null)
            this.root = null;
        else
        {
            this.root = copyTree(cafe.root);
        }
    }

    // helper method to recursively copy the tree
    private CatNode copyTree(CatNode node)
    {
        if (node == null)
        {
            return null; // base case
        }

        // create new node with same Cat object (shallow copy)
        CatNode newNode = new CatNode(node.catEmployee);

        // recursively copy
        newNode.senior = copyTree(node.senior);
        newNode.junior = copyTree(node.junior);

        // set parent pointers
        if (newNode.senior != null) {
            newNode.senior.parent = newNode;
        }
        if (newNode.junior != null) {
            newNode.junior.parent = newNode;
        }

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

    // returns a list of cats containing the top numOfCatsToHonor cats
    // in the cafe with the thickest fur. Cats are sorted in descending
    // order based on their fur thickness.
    public ArrayList<Cat> buildHallOfFame(int numOfCatsToHonor) {
        ArrayList<Cat> allCats = new ArrayList<Cat>();
        ArrayList<Cat> hallOfFame = new ArrayList<Cat>();

        if (root == null || numOfCatsToHonor == 0)
            return hallOfFame;

        collectAllCats(root,allCats);

        if(allCats.size() > 1)
            mergeSort(allCats, 0, allCats.size() -1);

        int limit;
        if (numOfCatsToHonor < allCats.size())
            limit = numOfCatsToHonor;
        else
            limit = allCats.size();
        for (int i = 0; i < limit; i++)
        {
            hallOfFame.add(allCats.get(i));
        }
        return hallOfFame;
    }

    //helper method
    private void collectAllCats(CatNode node, ArrayList<Cat> cats) {
        if (node == null)
        {
            return;
        }
        cats.add(node.catEmployee);
        collectAllCats(node.senior, cats);
        collectAllCats(node.junior, cats);
    }

    //helper mergesort method
    private void mergeSort(ArrayList<Cat> cats, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(cats, left, mid);
            mergeSort(cats, mid + 1, right);
            merge(cats, left, mid, right);
        }
    }

    // merge helper - merges in descending order
    private void merge(ArrayList<Cat> cats, int left, int mid, int right) {
        ArrayList<Cat> temp = new ArrayList<>();
        int i = left, j = mid + 1;

        // merge in descending order (thickest fur first)
        while (i <= mid && j <= right) {
            if (cats.get(i).getFurThickness() >= cats.get(j).getFurThickness()) {
                temp.add(cats.get(i));
                i++;
            } else {
                temp.add(cats.get(j));
                j++;
            }
        }
        // add remaining elements from each half
        while (i <= mid) {
            temp.add(cats.get(i));
            i++;
        }
        while (j <= right) {
            temp.add(cats.get(j));
            j++;
        }

        // copy sorted elements back to original list
        for (int k = 0; k < temp.size(); k++) {
            cats.set(left + k, temp.get(k));
        }
    }

    // Returns the expected grooming cost the cafe has to incur in the next numDays days
    public double budgetGroomingExpenses(int numDays)
    {
        if (root == null)
            return 0.0; // no expenses
        return budgetGroomingHelper(root, numDays);
    }

    private double budgetGroomingHelper(CatNode root, int numDays)
    {
        if (root == null)
            return 0.0;

        double totalCost = 0.0;

        if (root.catEmployee.getDaysToNextGrooming() <= numDays) // if needs grooming within the next days
        {
            totalCost = totalCost + root.catEmployee.getExpectedGroomingCost();
        }
        totalCost = budgetGroomingHelper(root.senior, numDays);
        totalCost = budgetGroomingHelper(root.junior, numDays);

        return totalCost;
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

        /*
        int maxWeeks = 0;
        for (int i = 0; i < allCats.size(); i++)
        {
            int weeks = (allCats.get(i).getDaysToNextGrooming())/7; // 7 days in a week
            if (weeks > maxWeeks)
            {
                maxWeeks = weeks;
            }
        }

        for (int i = 0; i<= maxWeeks; i++)
        {
            schedule.add(new ArrayList<>()); // next ArrayList added
        }

        for (int i = 0; i < allCats.size(); i ++)
        {
            Cat cat = allCats.get(i);
            int weekIndex = (cat.getDaysToNextGrooming())/7;
            schedule.get(weekIndex).add(cat);
        }
        return schedule;

         */
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
        public CatNode hire (Cat c) {
            int comparison = c.compareTo(this.catEmployee);

            if (comparison > 0) { // c is more senior (smaller monthHired)
                if (this.senior == null) {
                    this.senior = new CatNode(c);
                    this.senior.parent = this;
                } else {
                    this.senior = this.senior.hire(c); // call again
                }
            } else {
                if (this.junior == null) {
                    this.junior = new CatNode(c);
                    this.junior.parent = this;
                } else {
                    this.junior = this.junior.hire(c); // call again
                }
            }

            // now account for the heap
            CatNode maxFurCat = null;
            if (this.senior != null && this.junior != null)
            {
                if (this.senior.catEmployee.getFurThickness() >= this.junior.catEmployee.getFurThickness()) {
                    maxFurCat = this.senior;
                } else {
                    maxFurCat = this.junior;
                }
            } else if (this.senior != null)
                maxFurCat = this.senior;
            else if (this.junior != null)
                maxFurCat = this.junior;

            if(maxFurCat != null && maxFurCat.catEmployee.getFurThickness() > this.catEmployee.getFurThickness())
            {
                if (maxFurCat == this.senior)
                    return rightRotate();
                else
                    return leftRotate();
            }

            return this;
        }

        // Right rotation: senior child becomes parent
        private CatNode rightRotate()
        {
            CatNode newRoot = this.senior;
            this.senior = newRoot.junior;

            // Update parent pointers
            if (this.senior != null) {
                this.senior.parent = this;
            }

            newRoot.junior = this;
            newRoot.parent = this.parent;
            this.parent = newRoot;

            return newRoot;
        }

        // Left rotation: junior child becomes parent
        private CatNode leftRotate()
        {
            CatNode newRoot = this.junior;
            this.junior = newRoot.senior;

            // Update parent pointers
            if (this.junior != null) {
                this.junior.parent = this;
            }

            newRoot.senior = this;
            newRoot.parent = this.parent;
            this.parent = newRoot;

            return newRoot;
        }
        // remove c from the tree rooted at this and returns the root of the resulting tree
        public CatNode retire(Cat c) {
            /*
             * TODO: ADD YOUR CODE HERE
             */
            return null;
        }

        // find the cat with highest seniority in the tree rooted at this
        public Cat findMostSenior()
        {
            if(this.senior == null)
                return this.catEmployee;
            return this.findMostSenior();
        }

        // find the cat with lowest seniority in the tree rooted at this
        public Cat findMostJunior()
        {
            if (this.junior == null)
                return this.catEmployee;
            return this.findMostJunior();
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


