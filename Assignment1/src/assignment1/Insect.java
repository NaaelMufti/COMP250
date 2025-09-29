package assignment1;
// Naael Mufti, McGill ID: 261279652

public abstract class Insect
{
    private Tile position;
    private int health;

    public Insect(Tile pos, int hp)
    {
        this.position = pos;
        this.health = hp;

        if (pos != null)
        {
            boolean test = pos.addInsect(this);
            if (test == false)
            {
                throw new IllegalArgumentException("Cannot place the insect on this tile. One bee per tile, and a hornet can only be placed on the path.");
            }
        }
    }



    public final Tile getPosition()
    {
        return position;
    }

    public final int getHealth()
    {
        return health;
    }

    public void setPosition(Tile pos)
    {
        this.position = pos;
    }


}
