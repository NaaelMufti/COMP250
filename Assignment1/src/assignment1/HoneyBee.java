package assignment1;
// Naael Mufti, McGill ID: 261279652

public abstract class HoneyBee extends Insect // subclass of Insect
{
    private int cost; // cost is in food

    public HoneyBee(Tile pos, int hp, int cost)
    {
        super(pos,hp); // fields are private inside Insect
        this.cost = cost;
    }


    public int getCost()
    {
        return cost;
    }
}
