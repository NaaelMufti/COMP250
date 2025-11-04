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

    /*
    public void move(Position p)
    {
        if (Position.getDistance(this.getHeadPosition(), p) != 1)
        {
            throw new IllegalArgumentException("Error: Caterpillar can only move 1 tile orthogonally to the head.");
        }

        if (this.stage == EvolutionStage.ENTANGLED) // cant do anything
        {
            throw new IllegalArgumentException("Error: Caterpillar is Entangled, game over.");
        }

        for (Segment collCheck = this.head; collCheck != null; collCheck = collCheck.next) // check collision
        {
            if (collCheck.position.equals(p))
            {
                this.stage = EvolutionStage.ENTANGLED;
                return;
            }
        }

        this.positionsPreviouslyOccupied.push(new Position(this.head.position)); // old head changes position first

        Position prev = new Position(this.head.position);
        Segment cur = this.head.next;
        while (cur != null) {
            Position temp = new Position(cur.position);
            cur.position = prev;
            prev = temp;
            cur = cur.next;
        }

        this.head.position = new Position(p); // change new head

        // digestion handling (cake question)
        if (this.stage == EvolutionStage.GROWING_STAGE && this.turnsNeededToDigest > 0) {
            Color newColour = GameColors.SEGMENT_COLORS[randNumGenerator.nextInt(5)];
            Segment newSeg = new Segment(new Position(this.tail.position), newColour);
            this.tail.next = newSeg;
            this.tail = newSeg;
            this.length++;
            this.turnsNeededToDigest--;

            if (this.length >= this.goal)
                this.stage = EvolutionStage.BUTTERFLY;
        } else if (this.stage == EvolutionStage.GROWING_STAGE && this.turnsNeededToDigest == 0) {
            this.stage = EvolutionStage.FEEDING_STAGE;
        }
    }


     */


    public void move(Position p) {
        // Validate move distance
        if (Position.getDistance(this.getHeadPosition(), p) != 1)
            throw new IllegalArgumentException("Error: Caterpillar can only move 1 tile orthogonally to the head.");

        if (this.stage == EvolutionStage.ENTANGLED)
            throw new IllegalArgumentException("Error: Caterpillar is Entangled, game over.");

        // Check self-collision
        for (Segment check = this.head; check != null; check = check.next) {
            if (check.position.equals(p)) {
                this.stage = EvolutionStage.ENTANGLED;
                return;
            }
        }

        // Save tail’s position before moving
        positionsPreviouslyOccupied.push(new Position(this.tail.position));

        // Store old positions while traversing forward
        Position prev = new Position(this.head.position);
        Segment cur = this.head.next;

        while (cur != null) {
            Position temp = new Position(cur.position);
            cur.position = prev;
            prev = temp;
            cur = cur.next;
        }

        // Finally move head
        this.head.position = new Position(p);

        // Handle digestion
        if (stage == EvolutionStage.GROWING_STAGE && turnsNeededToDigest > 0) {
            Position newPos = !positionsPreviouslyOccupied.empty()
                    ? positionsPreviouslyOccupied.pop()
                    : new Position(tail.position);

            Color newColor = GameColors.SEGMENT_COLORS[
                    randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length)
                    ];

            Segment newSeg = new Segment(newPos, newColor);
            tail.next = newSeg;
            tail = newSeg;
            length++;
            turnsNeededToDigest--;

            if (turnsNeededToDigest == 0 && stage != EvolutionStage.BUTTERFLY) {
                stage = EvolutionStage.FEEDING_STAGE;
            }
        }
    }




    // a segment of the fruit's color is added at the end
    public void eat(Fruit f)
    {
        Position newSegPos = this.positionsPreviouslyOccupied.pop(); // from MyStack
        Segment newSeg = new Segment(newSegPos, f.getColor()); // new segment with color of fruit
        this.tail.next = newSeg;
        this.tail = newSeg;
        this.length++; // update relevant fields

        if (this.length >= this.goal)
        {
            this.stage = EvolutionStage.BUTTERFLY;
        }
    }

    // the caterpillar moves one step backwards because of sournes
    public void eat(Pickle p)
    {
        if (this.length < 2) return; // only 1 seg

        Position[] oldPositions = this.getPositions(); // copy the positions

        Segment cur = this.head;  // move the head
        for (int i = 1; i < oldPositions.length; i++)
        {
            cur.position = new Position(oldPositions[i]);
            cur = cur.next;
        }

        if (!positionsPreviouslyOccupied.empty()) // move the tail
        {
            this.tail.position = this.positionsPreviouslyOccupied.pop();
        }
    }


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



    //18/18AM, 7/11 M
    public void eat(SwissCheese cheese)
    {
        // if caterpillar has one segment, it can’t shrink
        if (this.length <= 1) return;

        Color[] oldColors = this.getColors();
        int newLength = (this.length + 1) / 2; // keep half, rounded up

        // Traverse to the node that will become the new tail
        Segment cur = this.head;
        for (int i = 1; i < newLength; i++)
        {
            cur.color = oldColors[i + i];
            cur = cur.next;
        }

        // Cut off the rest
        Segment cut = cur.next;
        cur.next = null;
        this.tail = cur;
        this.length = newLength;

        while (cut != null)
        {
            this.positionsPreviouslyOccupied.push(cut.position);
            cut = cut.next;
        }
    }


    /*
    Minitests pass, but runtime error 17/18AM, 6/11 mastery
    public void eat(SwissCheese cheese) {
        // If it has only one segment, nothing changes
        if (this.length <= 1) return;

        // Compute new length (lose half)
        int newLength = (this.length + 1) / 2;

        // Get arrays before modifying
        Position[] oldPositions = this.getPositions();
        Color[] oldColors = this.getColors();

        // We’ll keep the *last* newLength positions — this keeps the body compact & connected
        int start = this.length - newLength;

        // Rebuild the caterpillar body
        Segment cur = this.head;
        for (int i = 0; i < newLength; i++) {
            cur.position = new Position(oldPositions[start + i]);
            cur.color = oldColors[start + i];
            if (i == newLength - 1) {
                this.tail = cur; // mark new tail
            }
            cur = cur.next;
        }

        // Properly terminate chain
        this.tail.next = null;
        this.length = newLength;

        // Rebuild positionsPreviouslyOccupied to include trimmed-off old positions
        this.positionsPreviouslyOccupied = new MyStack<Position>();
        for (int i = 0; i < start; i++) {
            this.positionsPreviouslyOccupied.push(new Position(oldPositions[i]));
        }
    }

     */

    /*
    public void eat(SwissCheese cheese) 4th version
    {
        // If caterpillar has only one segment, nothing to remove
        if (this.length <= 1) return;

        // Keep every other segment (starting from head)
        Segment cur = this.head;
        Segment prev = null;

        int index = 0;
        int newLength = 0;

        while (cur != null) {
            if (index % 2 == 0) {
                // Keep this node
                newLength++;
                prev = cur;
                cur = cur.next;
            } else {
                // Skip this node and reconnect
                if (prev != null) {
                    prev.next = cur.next;
                }
                cur = cur.next;
            }
            index++;
        }

        // Ensure tail points to last kept node and terminates properly
        if (prev != null) {
            this.tail = prev;
            this.tail.next = null;
        }

        // Update actual length
        this.length = newLength;
    }

     */

    /*
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

     */

    /*
    public void eat(SwissCheese cheese) { 3rd version
        Position[] positions = this.getPositions();
        Color[] colors = this.getColors();

        int newLength = (this.length + 1) / 2;  // lose half, rounding up

        // keep every other segment (0, 2, 4, ...)
        Segment cur = this.head;
        Segment prev = null;
        int index = 0;
        int count = 0;

        while (cur != null && count < newLength) {
            if (index % 2 == 0) {
                // keep this segment
                prev = cur;
                count++;
            } else {
                // skip this segment
                if (prev != null) {
                    prev.next = cur.next;
                }
            }
            cur = cur.next;
            index++;
        }

        // close off the tail
        if (prev != null) {
            this.tail = prev;
            this.tail.next = null;
        }

        this.length = newLength;
    }

     */

    /*
    public void eat(SwissCheese cheese) 2nd Version
    {
        if (length <= 1) return;

        Position[] oldPositions = getPositions();
        Color[] oldColors = getColors();

        // New half-length (rounding up)
        int newLength = (length + 1) / 2;

        Segment cur = head;
        for (int i = 0; i < newLength; i++) {
            cur.position = new Position(oldPositions[i * 2]);
            cur.color = oldColors[i * 2];
            if (i == newLength - 1) {
                tail = cur;
                tail.next = null;
            } else {
                cur = cur.next;
            }
        }

        // Update length
        length = newLength;

        // Rebuild stack with surviving positions (oldest at bottom)
        MyStack<Position> newStack = new MyStack<>();
        for (int i = 0; i < oldPositions.length; i += 2)
            newStack.push(new Position(oldPositions[i]));
        positionsPreviouslyOccupied = newStack;
    }

     */

    /*
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

     */

    /*
    public void eat(Cake cake) { 2nd Version
        // A butterfly cannot eat anymore.
        if (this.stage == EvolutionStage.BUTTERFLY) {
            return;
        }

        // Increase the total digestion turns by the cake's nutrition value
        this.turnsNeededToDigest += cake.getEnergyProvided();

        // If we just started digesting, enter the growing stage
        if (this.turnsNeededToDigest > 0) {
            this.stage = EvolutionStage.GROWING_STAGE;
        }

        // When digestion completes (i.e., moves later reduce turnsNeededToDigest to 0),
        // the stage will be reset to FEEDING_STAGE inside move().
        // But if digestion is already complete right now, ensure stage reflects that:
        if (this.turnsNeededToDigest == 0 && this.stage != EvolutionStage.BUTTERFLY) {
            this.stage = EvolutionStage.FEEDING_STAGE;
        }
    }

     */

    /*
    public void eat(Cake cake) {
        if (this.stage == EvolutionStage.BUTTERFLY) return;

        this.stage = EvolutionStage.GROWING_STAGE;
        int energyLeft = cake.getEnergyProvided();

        // temporary stack to reverse order (to grow tail-first)
        MyStack<Position> temp = new MyStack<>();
        while (!positionsPreviouslyOccupied.empty()) {
            temp.push(positionsPreviouslyOccupied.pop());
        }

        while (energyLeft > 0 && !temp.empty()) {
            Position nextPos = temp.pop();

            // make sure it's not overlapping
            boolean occupied = false;
            Segment check = this.head;
            while (check != null) {
                if (check.position.equals(nextPos)) {
                    occupied = true;
                    break;
                }
                check = check.next;
            }

            if (!occupied) {
                Color newColor = GameColors.SEGMENT_COLORS[
                        randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length)
                        ];
                Segment newSeg = new Segment(new Position(nextPos), newColor);
                this.tail.next = newSeg;
                this.tail = newSeg;
                this.length++;
                energyLeft--;

                if (this.length >= this.goal) {
                    this.stage = EvolutionStage.BUTTERFLY;
                    return;
                }
            }
        }

        // whatever positions remain get pushed back to main stack
        while (!temp.empty()) {
            positionsPreviouslyOccupied.push(temp.pop());
        }

        this.turnsNeededToDigest = energyLeft;
        if (this.turnsNeededToDigest == 0 && this.stage != EvolutionStage.BUTTERFLY) {
            this.stage = EvolutionStage.FEEDING_STAGE;
        }
    }

     */


    public void eat(Cake cake) // 18/18 AM, 7/11 M
    {
        if (this.stage == EvolutionStage.BUTTERFLY) return;

        this.stage = EvolutionStage.GROWING_STAGE;
        int energyLeft = cake.getEnergyProvided();

        // grow immediately using previously occupied positions
        while (energyLeft > 0 && !positionsPreviouslyOccupied.empty()) {
            Position nextPos = positionsPreviouslyOccupied.pop();

            // ensure we're not placing over an existing segment
            boolean occupied = false;
            Segment check = this.head;
            while (check != null) {
                if (check.position.equals(nextPos)) {
                    occupied = true;
                    break;
                }
                check = check.next;
            }

            if (!occupied) {
                Color newColor = GameColors.SEGMENT_COLORS[randNumGenerator.nextInt(5)];
                Segment newSeg = new Segment(new Position(nextPos), newColor);
                this.tail.next = newSeg;
                this.tail = newSeg;
                this.length++;
                energyLeft--;

                if (this.length >= this.goal) {
                    this.stage = EvolutionStage.BUTTERFLY;
                    return;
                }
            }
        }

        // if still have leftover energy, digest it
        this.turnsNeededToDigest = energyLeft;

        // if fully digested, return to feeding
        if (this.turnsNeededToDigest == 0 && this.stage != EvolutionStage.BUTTERFLY) {
            this.stage = EvolutionStage.FEEDING_STAGE;
        }
    }



    public String toString()
    {
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