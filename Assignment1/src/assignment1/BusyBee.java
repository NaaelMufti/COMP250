package assignment1;
// Naael Mufti, McGill ID: 261279652

public class BusyBee extends HoneyBee // extends HoneyBee so also extends Insect
{
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public BusyBee(Tile pos)
    {
        super(pos, BASE_HEALTH, BASE_COST);
    }
}
