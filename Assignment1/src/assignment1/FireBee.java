package assignment1;
// Naael Mufti, McGill ID: 261279652

public class FireBee extends HoneyBee
{
    private int attackRange;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public FireBee(Tile pos, int attackRange)
    {
        super(pos, BASE_HEALTH, BASE_COST);
        this.attackRange = attackRange;
    }


    @Override
    public boolean takeAction()
    {
        // check if off the path or null, if so then false
        if(this.getPosition() == null || this.getPosition().isOnThePath() == false)
        {
            return false;
        }

        int rangeChecked = 1; // how much of its range we've checked so far
        Tile range = this.getPosition().towardsTheNest(); // doesn't start on its Tile

        while (range != null && rangeChecked <= attackRange && range.isNest() == false)
        {

            if (range.isOnFire() == false && range.getNumberOfHornets() > 0) // not on fire and exists
            {
                range.setOnFire();
                return true; // set on fire and return true
            }

            // move one more tile over
            range = range.towardsTheNest();
            rangeChecked = rangeChecked + 1;
        }
        // didn't find anything
        return false;
    }
}
