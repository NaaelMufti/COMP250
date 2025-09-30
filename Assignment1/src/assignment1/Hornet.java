package assignment1;
// Naael Mufti, McGill ID: 261279652

public class Hornet extends Insect // subclass of Insect
{
    private int attackDamage;
    public static int BASE_FIRE_DMG;

    private boolean isTheQueen;
    private static int numOfQueens; // shared by all instances

    public Hornet(Tile pos, int hp, int attackDamage)
    {
        super(pos,hp); // fields are private inside Insect
        this.attackDamage = attackDamage;
    }

    @Override
    public boolean takeAction()
    {
        int numOfTurns;
        if (this.isTheQueen)
        {
            numOfTurns = 2; // queen gets 2 turns
        } else
        {
            numOfTurns = 1;
        }

        for (int i = 0; i < numOfTurns; i++) {
            this.takeDamage(BASE_FIRE_DMG); // will be at start of every action regardless of outcome

            if (this.getPosition().getBee() != null) // if there is a bee
            {
                this.getPosition().getBee().takeDamage(this.attackDamage);
            } else if (this.getPosition().isHive()) // if already at Hive
            {
                return false;
            }
            // if other 2 cases aren't true then it moves towards the Hive
            else {
                Tile next = this.getPosition().towardTheHive(); // set to next tile towards the hive
                this.getPosition().removeInsect(this); // remove insect from original tile
                next.addInsect(this); // add to next tile
                this.setPosition(next); // update position
            } // if normal hornet will exit for loop and return true, if queen will go again
        }
        return true;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(super.equals(obj) == false) // Isn't insect or is null so can say false
        {
            return false;
        }

        if (obj instanceof Hornet) // if same type
        {
            Hornet first = (Hornet) obj;
            if (this.attackDamage == first.attackDamage) // same thing we did but add attack damage (pos and health already checked with super)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isTheQueen()
    {
        return this.isTheQueen;
    }

    public void promote()
    {
        if (numOfQueens == 0)
        {
            this.isTheQueen = true;
            numOfQueens = numOfQueens + 1;
        }
    }

    public static void main(String[] args)
    {

    }
}
