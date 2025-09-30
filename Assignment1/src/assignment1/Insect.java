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

    public void takeDamage(int damage)
    {
        this.health = this.health - damage;
        if (this.health <= 0 && this.position != null)
        {
            position.removeInsect(this);
        }
    }

    public abstract boolean takeAction();

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (this == obj) // check if using same reference then true
        {
            return true;
        }

        if (obj instanceof Insect) // if same type
        {
            Insect first = (Insect) obj; // Typecast object to Insect
            if ((this.health == first.health) && (this.position == first.position))
            {
                return true;
            }
        }
        return false;
    }

    public void regenerateHealth(double regenPercent)
    {
        if(regenPercent > 0)
        {
            this.health = (int) (this.health * (regenPercent / 100)); // int typecast to round down (truncate)
        }
    }


}
