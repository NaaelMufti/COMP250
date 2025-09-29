package assignment1;
// Naael Mufti, McGill ID: 261279652

public class AngryBee extends HoneyBee
{
    private int attackDamage;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public AngryBee (Tile pos, int attackDamage)
    {
        super(pos, BASE_HEALTH, BASE_COST);
        this.attackDamage = attackDamage;
    }
}
