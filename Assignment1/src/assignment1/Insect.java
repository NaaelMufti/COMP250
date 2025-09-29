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
