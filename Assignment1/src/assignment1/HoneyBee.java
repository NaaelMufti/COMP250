package assignment1;
// Naael Mufti, McGill ID: 261279652

public abstract class HoneyBee extends Insect // subclass of Insect
{
    private int cost; // cost is in food
    public static double HIVE_DMG_REDUCTION;

    public HoneyBee(Tile pos, int hp, int cost)
    {
        super(pos,hp); // fields are private inside Insect
        this.cost = cost;
    }

    public int getCost()
    {
        return cost;
    }

    @Override
    public void takeDamage(int damage)
    {
        if (this.getPosition().isHive() == true && this.getPosition() != null)
        {
            damage = (int) (damage * (1 - HIVE_DMG_REDUCTION)); // int typecast truncates the double (rounds down)
        }
        super.takeDamage(damage); // rest stays same with updated damage
    }


}
