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
        this.turnsNeededToDigest = 0;
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

    public Position[] getPositions(){
        Position[] ps = new Position[this.length];
        Segment chk = this.head;
        for (int i = 0; i < this.length; i++){
            ps[i] = chk.position;
            chk = chk.next;
        }
        return ps;
    }


    // shift all Segments to the previous Position while maintaining the old color
    // the length of the caterpillar is not affected by this
    public void move(Position p)
    {
        if(Position.getDistance(this.getHeadPosition(),p) != 1) // If not orthogonal
        {
            throw new IllegalArgumentException("Error: Caterpillar can only move 1 tile orthogonally to the head.");
        }

        if (this.stage == EvolutionStage.ENTANGLED) // if entangled can't move
        {
            throw new IllegalArgumentException("Error: Caterpillar is Entangled, game over.");
        }

        Segment collCheck = this.head; // collision
        while(collCheck != null)
        {
            if(collCheck.position.equals(p)) // if p is part of caterpillar
            {
                this.stage = EvolutionStage.ENTANGLED;
                return; // exit (void method)
            }
            collCheck = collCheck.next;
        }


        Position prev = new Position(getHeadPosition()); // shift all elements up
        Segment cur = this.head.next;
        while(cur != null)
        {
            Position temp = new Position(cur.position);
            cur.position = prev;
            prev = temp;
            cur = cur.next;
        }
        this.head.position = new Position(p); // update head to new position
        this.positionsPreviouslyOccupied.push(new Position(getHeadPosition()));

        // check for cake (last question)
        if(this.stage ==EvolutionStage.GROWING_STAGE && this.turnsNeededToDigest > 0)
        {
            Color newColour = GameColors.SEGMENT_COLORS[randNumGenerator.nextInt(5)];
            Segment newSeg = new Segment(new Position(this.tail.position), newColour);
            this.tail.next = newSeg;
            this.tail = newSeg;
            this.length++;
            this.turnsNeededToDigest--;

            if (this.length >= this.goal)
            {
                this.stage = EvolutionStage.BUTTERFLY;
            }
        }
        else if (this.stage == EvolutionStage.GROWING_STAGE && this.turnsNeededToDigest == 0)
        {
            this.stage = EvolutionStage.FEEDING_STAGE;
        }
    }



    // a segment of the fruit's color is added at the end
    public void eat(Fruit f)
    {
        Position newSegPos = this.positionsPreviouslyOccupied.pop(); // from MyStack
        Segment newSeg = new Segment(newSegPos, f.getColor()); // new segment w color of fruit
        this.tail.next = newSeg;
        this.tail = newSeg;
        this.length++; // update relevant fields

        if (this.length >= this.goal)
        {
            this.stage = EvolutionStage.BUTTERFLY;
        }
    }

    // the caterpillar moves one step backwards because of sourness
    public void eat(Pickle p)
    {
        if (this.positionsPreviouslyOccupied.empty()) {
            return;
        }

        // Move one step backward (head goes to last previously occupied position)
        Position backStep = this.positionsPreviouslyOccupied.pop();

        // Shift body positions accordingly
        Position prev = new Position(this.head.position);
        this.head.position = backStep;

        Segment cur = this.head.next;
        while (cur != null)
        {
            Position temp = new Position(cur.position);
            cur.position = prev;
            prev = temp;
            cur = cur.next;
        }
    }


    /*
  Position prevPos = this.positionsPreviouslyOccupied.pop(); // position to move head to
        Segment newSeg = this.head;
        Position tempPrev;
        while (newSeg != null) {
            tempPrev = newSeg.position;
            newSeg.position = prevPos;
            prevPos = tempPrev;
            newSeg = newSeg.next;
        }
     */

    //for (int i = 0; i < this.getLength(); i++)
    //        {
    //            newSeg.position = oldPositions[i-1]; // make prev position
    //            newSeg = newSeg.next;
    //        }

    // all the caterpillar's colors shuffle around
    public void eat(Lollipop lolly)
    {
        Color[] colours = getColors();

        for(int i = colours.length - 1; i > 0; i--) // random swap from end of array
        {
            int j = randNumGenerator.nextInt(i + 1); // j <= i so i+1
            Color temp = colours[i];
            colours[i] = colours[j];
            colours[j] = temp;
        }

        Segment newSeg = this.head; // already shuffled, can just go in order and assign
        for (int i = 0; i < this.getLength(); i++)
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
        Segment oldHead = this.head;
        Segment next = null;
        while(cur != null)
        {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        this.head = prev;
        this.tail = oldHead;

        this.head.color= GameColors.BLUE; // update to blue
        this.positionsPreviouslyOccupied = new MyStack<Position>(); // new empty stack
    }

    // the caterpillar embodies a slide of Swiss cheese loosing half of its segments.
    public void eat(SwissCheese cheese)
    {
        Position[] positions = this.getPositions();
        Color[] colours = this.getColors();
        int newLength = (this.getLength()+1)/2; // round up

        int start = this.getLength() - newLength; // new head starts

        Color[] newColours = new Color[newLength]; // update the new colours
        for (int i = 0; i < newLength; i++)
        {
            int colourI = 2*i; // every other colour (i * 2)
            if (colourI >= this.getLength())
            {
                colourI = length - 1; // incase its odd
            }
            newColours[i] = colours[colourI];
        }

        Segment cur = this.head; // now shrink the body
        for (int i = 0; i < newLength; i ++)
        {
            cur.position = new Position(positions[start + i]);
            cur.color = newColours[i];
            if (i == newLength - 1) // reached last segment
            {
                this.tail = cur;
            }
            cur = cur.next;
        }
        this.tail.next = null; // disconnect the rest
        this.length = newLength;

        for (int i = 0; i < start; i++) // update prev occupied stack
        {
            this.positionsPreviouslyOccupied.push(new Position(positions[i]));
        }
    }

    public void eat(Cake cake)
    {
        this.stage = EvolutionStage.GROWING_STAGE; // set stage
        int energyLeft = cake.getEnergyProvided();

        while (energyLeft > 0 && !positionsPreviouslyOccupied.empty()) // keep growing while energy and positions available
        {
            Position nextPos = positionsPreviouslyOccupied.pop();

            Segment cur = head;
            boolean occ = false;
            while(cur != null)
            {
                if(cur.position.equals(nextPos)) // check if gus occupies a position
                {
                    occ = true;
                    break;
                }
                cur = cur.next;
            }

            if (occ == false) // if not occupied then we can grow
            {
                Color newColour = GameColors.SEGMENT_COLORS[randNumGenerator.nextInt(5)];
                Segment newSeg = new Segment(nextPos, newColour);
                tail.next = newSeg;
                tail = newSeg;
                energyLeft--;
                length++;

                if (length >= goal)
                {
                    stage = EvolutionStage.BUTTERFLY;
                    return;
                }
            }
        }
        this.turnsNeededToDigest = energyLeft;

        if (energyLeft == 0 && this.stage != EvolutionStage.BUTTERFLY)
        {
            this.stage = EvolutionStage.FEEDING_STAGE; // edge case
        }
    }

    public String toString() {
        Segment s = this.head;
        String snake = "";
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

        gus.move(new Position(3,1));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position(2,1));
        gus.move(new Position(1,1));
        gus.eat(new Fruit(GameColors.YELLOW));


        System.out.println("\n2) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(1,2));
        gus.eat(new IceCream());

        System.out.println("\n3) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(3,1));
        gus.move(new Position(3,2));
        gus.eat(new Fruit(GameColors.ORANGE));


        System.out.println("\n4) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2,2));
        gus.eat(new SwissCheese());

        System.out.println("\n5) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2, 3));
        gus.eat(new Cake(4));

        System.out.println("\n6) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
    }
}