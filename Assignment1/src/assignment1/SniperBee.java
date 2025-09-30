package assignment1;
// Naael Mufti, McGill ID: 261279652

public class SniperBee extends HoneyBee
{
    private int attackDamage;
    private int piercingPower;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    private int turnCount = 0;

    public SniperBee(Tile pos, int attackDamage, int piercingPower)
    {
        super(pos, BASE_HEALTH, BASE_COST);
        this.attackDamage = attackDamage;
        this.piercingPower = piercingPower;
    }

    @Override
    public boolean takeAction()
    {
        // if null or not on path then false
        if (this.getPosition() == null || this.getPosition().isOnThePath() == false)
        {
            return false;
        }

        turnCount = turnCount + 1;

        if(turnCount % 2 == 1) // eg turn 1
        {
            return false; // do nothing
        }

        int n; // variable to find which is smaller of piercing power and number of hornets
        if (piercingPower > this.getPosition().towardTheNest().getNumOfHornets())
        {
            n = piercingPower;
        } else
        {
            n = this.getPosition().towardTheNest().getNumOfHornets();
        } // set n to either piercing power or numb of hornets

        // now every other turn (turnCount % 2 == 0)
        while (this.getPosition().towardTheNest() != null && this.getPosition().towardTheNest().isNest() == false)
        {
            for (int i = 0; i < n; i++)
            {
                Hornet h = this.getPosition().towardTheNest().getHornets()[i]; // access the array element
                h.takeDamage(attackDamage); // the hornet in place i takes damage
            }
        }
        return false; // nobody to hit so returns false
    }
}
