// Naael Mufti, McGill ID: 261279652

package assignment2;

import java.awt.Color;
import java.util.Random;

import assignment2.food.*;


public class Caterpillar
{

    // This nested class was declared public for testing purposes
    public class Segment
    {
        private Position position;
        private Color color;
        private Segment next;

        public Segment(Position p, Color c)
        {
            this.position = p;
            this.color = c;
        }

    }

    // All the fields have been declared public for testing purposes
    public Segment head;
    public Segment tail;
    public int length;
    public EvolutionStage stage;

    public MyStack<Position> positionsPreviouslyOccupied;
    public int goal;
    public int turnsNeededToDigest;

    public static Random randNumGenerator = new Random(1);

    // Creates a Caterpillar with one Segment. It is up to students to decide how to implement this.
    public Caterpillar(Position p, Color c, int goal)
    {
        Segment firstSeg = new Segment(p, c); // sets position and color
        this.goal = goal; // sets goal

        this.head = firstSeg;
        this.tail = firstSeg;
        this.length = 1;
        this.stage = EvolutionStage.FEEDING_STAGE;
        this.positionsPreviouslyOccupied = new MyStack<Position>();
    }

    public EvolutionStage getEvolutionStage()
    {
        return this.stage;
    }

    public Position getHeadPosition()
    {
        return this.head.position;
    }

    public int getLength()
    {
        return this.length;
    }


    // returns the color of the segment in position p. Returns null if such segment does not exist
    public Color getSegmentColor(Position p)
    {
        Segment cur = this.head;
        while (cur != null)
        {
            if(cur.position.equals(p)) // when finds position
            {
                return cur.color;
            }
            cur = cur.next;
        }
        return null; // no position, so null
    }


    // Methods that need to be added for the game to work
    public Color[] getColors(){
        Color[] cs = new Color[this.length];
        Segment chk = this.head;
        for (int i = 0; i < this.length; i++){
            cs[i] = chk.color;
            chk = chk.next;
        }
        return cs;
    }

    public Position[] getPositions()
    {
        Position[] ps = new Position[this.length];
        Segment chk = this.head;
        for (int i = 0; i < this.length; i++){
            ps[i] = chk.position;
            chk = chk.next;
        }
        return ps;
    }

    // private helper method for occupied positions
    private boolean isPosOccupied(Position position)
    {
        Segment curSeg = this.head;
        while (curSeg != null)
        {
            if(curSeg.position.equals(position))
            {
                return true;
            }
            curSeg = curSeg.next;
        }
        return false;
    }

    //private helper method to add a random coloured segment
    private void addRandomSeg()
    {
        if (positionsPreviouslyOccupied.empty())
            return;

        Color randColor = GameColors.SEGMENT_COLORS[randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length)]; // generate random color
        Segment newSeg = new Segment(positionsPreviouslyOccupied.pop(), randColor);
        // add to end
        tail.next = newSeg;
        tail = newSeg;
        length++;
    }

    public void move(Position p)
    {
        if (Position.getDistance(head.position, p) > 1)
        {
            throw new IllegalStateException("Error: Caterpillar can only move 1 tile orthogonally to the head.");
        }
        if(Position.getDistance(head.position, p) == 0)
        {
            return;
        }

        Segment curSeg = this.head;
        Position newPos = p;
        while (curSeg != null)
        {
            // if p occupied
            if(!tail.position.equals(p) && curSeg.position.equals(p))
            {
                stage = EvolutionStage.ENTANGLED;
                return;
            }

            // update position
            Position tempPos = curSeg.position;
            curSeg.position = newPos;
            newPos = tempPos;
            curSeg = curSeg.next;
        }

        positionsPreviouslyOccupied.push(newPos);
        if (turnsNeededToDigest > 0 && isPosOccupied(positionsPreviouslyOccupied.peek()))
        {
            addRandomSeg();
            turnsNeededToDigest--; // update turns
            if (length >= goal)
            {
                stage = EvolutionStage.BUTTERFLY;
                turnsNeededToDigest = 0;
            }
        }

        if (stage == EvolutionStage.GROWING_STAGE && turnsNeededToDigest == 0)
            stage = EvolutionStage.FEEDING_STAGE;

    }


    // a segment of the fruit's color is added at the end
    public void eat(Fruit f)
    {
        Position newSegPos = this.positionsPreviouslyOccupied.pop(); // from MyStack
        Segment newSeg = new Segment(newSegPos, f.getColor()); // new segment with color of fruit

        this.tail.next = newSeg;
        this.tail = newSeg;
        length++; // update relevant fields

        if (length >= goal)
        {
            stage = EvolutionStage.BUTTERFLY;
        }
    }

    // the caterpillar moves one step backwards because of sourness
    public void eat(Pickle p)
    {
        Segment curSeg = this.head;
        while (curSeg.next != null)
        {
            Segment nextSeg = curSeg.next;
            curSeg.position = nextSeg.position;
            curSeg = nextSeg;
        }
        curSeg.position = positionsPreviouslyOccupied.pop();
    }

    // all the caterpillar's colors shuffle around
    public void eat(Lollipop lolly)
    {
        Color[] colours = getColors();

        for(int i = length - 1; i > 0; i--) // random swap from end of array
        {
            int j = randNumGenerator.nextInt(i + 1); // j <= i so i+1
            Color temp = colours[i];
            colours[i] = colours[j];
            colours[j] = temp;
        }

        Segment newSeg = this.head; // already shuffled, can just go in order and assign
        for (int i = 0; i < this.length; i++)
        {
            newSeg.color = colours[i];
            newSeg = newSeg.next;
        }
    }

    // brain freeze!!
    // It reverses and its (new) head turns blue
    public void eat(IceCream gelato)
    {
        // Reverse Linked List
        Segment prev = null;
        Segment cur = this.head;
        while(cur != null)
        {
            Segment next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        Segment temp = head;
        head = tail;
        tail = temp;

        head.color= GameColors.BLUE; // update to blue
        positionsPreviouslyOccupied.clear(); // clear stack
    }

    // the caterpillar embodies a slide of Swiss cheese loosing half of its segments.
    public void eat(SwissCheese cheese)
    {
        if (length < 2)
            return;

        Segment fastP = this.head;
        Segment slowP = this.head;
        while(fastP.next.next != null)
        {
            fastP = fastP.next.next;
            slowP = slowP.next;
            slowP.color = fastP.color; // adjust to every other color
            length--;
        }

        tail = slowP;
        MyStack<Position> newStack = new MyStack<>();
        while(slowP.next!= null)
        {
            slowP = slowP.next;
            newStack.push(slowP.position);
        }

        while (!newStack.empty())
            positionsPreviouslyOccupied.push(newStack.pop());

        tail.next = null;
        length--;
    }

    public void eat(Cake cake)
    {
        stage = EvolutionStage.GROWING_STAGE;
        int energy = cake.getEnergyProvided();
        int growthAmount = 0;

        for (int i = 0; i < energy && !positionsPreviouslyOccupied.empty(); i++) // keep growing by the energy or no more to grow
        {
            boolean occupied = isPosOccupied(positionsPreviouslyOccupied.peek());

            if(occupied) {
                turnsNeededToDigest = energy - growthAmount;
                return;
            }
            addRandomSeg(); // add a new segment for every energy
            growthAmount++; // update relevant fields

            if (length >= goal) // once goal has been reached turn into butterfly
            {
                    stage = EvolutionStage.BUTTERFLY;
                    return;
            }
        }

        if (growthAmount == energy) // all energy consumed
        {
            stage = EvolutionStage.FEEDING_STAGE;
            turnsNeededToDigest = 0;
        } else // cannot consume all energy
        {
            turnsNeededToDigest = energy - growthAmount;
        }
    }


    public String toString()
    {
        Segment s = this.head;
        String snake = "head";
        while (s!=null) {
            String coloredPosition = GameColors.colorToANSIColor(s.color) +
                    s.position.toString() + GameColors.colorToANSIColor(Color.WHITE);
            snake = coloredPosition + " " + snake;
            s = s.next;
        }
        return snake;
    }



    public static void main(String[] args) {
        Position startingPoint = new Position(3, 2);
        Caterpillar gus = new Caterpillar(startingPoint, GameColors.GREEN, 10);
        System.out.println("1) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
        gus.move(new Position(3, 1));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position(2, 1));
        gus.move(new Position(1, 1));
        gus.eat(new Fruit(GameColors.YELLOW));
        System.out.println("\n2) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
        gus.move(new Position(1, 2));
        gus.eat(new IceCream());
        System.out.println("\n3) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
        gus.move(new Position(3, 1));
        gus.move(new Position(3, 2));
        gus.eat(new Fruit(GameColors.ORANGE));
        System.out.println("\n4) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
        gus.move(new Position(2, 2));
        gus.eat(new SwissCheese());
        System.out.println("\n5) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
        gus.move(new Position(2, 3));
        gus.eat(new Cake(4));
        System.out.println("\n6) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
    }
}