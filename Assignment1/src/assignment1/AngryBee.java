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

    @Override
    public boolean takeAction()
    {
        // not on Path or null so nothing happens
        if(this.getPosition() == null || this.getPosition().isOnThePath() == false)
        {
            return false;
        }

        // if current tile has hornets and not a nest
        if(this.getPosition().getNumberOfHornets() > 0 && this.getPosition().isNest() == false)
        {
            Hornet enemy = this.getPosition().getHornet(); // make the hornet an enemy
            enemy.takeDamage(this.attackDamage);
            return true; // successful so true
        }

        // not on the current tile, so checks the next tile
        if(this.getPosition().towardsTheNest() != null && this.getPosition().towardsTheNest().isNest() == false) // if next tile isn't null or a nest
        {
            if (this.getPosition().towardsTheNest().getNumberOfHornets() > 0) // not empty
            {
                Hornet enemy = this.getPosition().towardsTheNest().getHornet();
                enemy.takeDamage(this.attackDamage);
                return true; // successful so true
            }
        }

        // nothing there so returns false
        return false;
    }
}
