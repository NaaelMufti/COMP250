package assignment1;
// Naael Mufti, McGill ID: 261279652

public class Hornet extends Insect // subclass of Insect
{
    private int attackDamage;

    public Hornet(Tile pos, int hp, int attackDamage)
    {
        super(pos,hp); // fields are private inside Insect
        this.attackDamage = attackDamage;
    }

    public static void main(String[] args)
    {

    }
}
