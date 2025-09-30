package assignment1;
// Naael Mufti, McGill ID: 261279652

public class Tile
{
    private int food; // need food to add bee
    private boolean isHive;
    private boolean isNest;
    private boolean onThePath;
    private Tile towardTheHive;
    private Tile towardTheNest;
    private HoneyBee bee;
    private SwarmOfHornets swarm;

    private boolean onFire;

    public Tile()
    {
        this.isHive = false;
        this.isNest = false;
        this.onThePath = false;
        this.towardTheHive = null;
        this.towardTheNest = null;

        this.food = 0;
        this.bee = null;
        this.swarm = null;
    }

    public Tile(int food, boolean isHive, boolean isNest, boolean onThePath, Tile towardTheHive, Tile towardTheNest, HoneyBee bee, SwarmOfHornets swarm)
    {
        this.food = food;
        this.isHive = isHive;
        this.isNest = isNest;
        this.onThePath = onThePath;
        this.towardTheHive = towardTheHive;
        this.towardTheNest = towardTheNest;
        this.bee = bee;
        this.swarm = swarm;
    }

    public boolean isHive()
    {
        return this.isHive;
    }

    public boolean isNest()
    {
        return this.isNest;
    }

    public void buildHive()
    {
        this.isHive = true; // update from false to true
    }

    public void buildNest()
    {
        this.isNest = true; // update from false to true
    }

    public boolean isOnThePath()
    {
        return this.onThePath;
    }

    public Tile towardTheHive()
    {
        if (this.onThePath == false || this.isHive)
        {
            return null;
        } else
            return this.towardTheHive;
    }

    public Tile towardTheNest()
    {
        if (this.onThePath == false || this.isNest)
        {
            return null;
        } else
            return this.towardTheNest;
    }

    public void createPath(Tile pathHive, Tile pathNest)
    {
        if ((pathHive == null && this.isHive == false) || (pathNest == null && this.isNest == false)) // if either null and not hive or nest
        {
            throw new IllegalArgumentException("The path is invalid, no input received for a tile which isn't a hive or nest.");
        }
        this.towardTheHive = pathHive; // set as towards either
        this.towardTheNest = pathNest;
        this.onThePath = true;
    }

    public int collectFood()
    {
        int collectedFood = this.food;
        this.food = 0; // takes all food on Tile
        return collectedFood;
    }

    public void storeFood(int amtOfFood)
    {
        if (amtOfFood <= 0)
        {
            throw new IllegalArgumentException("Amount of food should be a positive integer greater then 0");
        }
        this.food = this.food + amtOfFood;
    }

    public int getNumberOfHornets()
    {
        if (this.swarm == null)
        {
            return 0;
        }
        return this.swarm.sizeOfSwarm(); // sizeOfSwarm defined in SwarmOfHornets
    }

    public HoneyBee getBee()
    {
        return this.bee;
    }

    public Hornet getHornet()
    {
        if (this.swarm == null)
        {
            return null;
        }
        return this.swarm.getFirstHornet();
    }

    public Hornet[] getHornets()
    {
        if (this.swarm == null)
        {
            return new Hornet[0]; // return an empty array
        }
        return this.swarm.getHornets(); // shallow copy of the array
    }

    public boolean addInsect (Insect ins)
    {
        if (ins instanceof HoneyBee) // cater to HoneyBee first
        {
            HoneyBee bee = (HoneyBee) ins; // downcasting

            if(this.isNest == true || this.bee != null)
            {
                return false; // cannot place if a hornets nest or if there already is a bee
            }
            if(this.isNest == false && this.bee == null)
            {
                this.bee = bee;
                bee.setPosition(this); // update pos of bee
                return true;
            }
        }
        else if (ins instanceof Hornet) // now if its a Hornet
        {
            Hornet hornet = (Hornet) ins; // downcasting

            if(this.onThePath == false)
            {
                return false;
            }

            if (this.swarm == null)
            {
                this.swarm = new SwarmOfHornets(); // if swarm is null create one
            }

            this.swarm.addHornet(hornet);
            hornet.setPosition(this);
            return true;
        }
        return false;
    }



    public boolean removeInsect(Insect ins)
    {
        if (ins == this.bee) // Insect can be a HoneyBee or Swarm of Hornets
        {
            this.bee.setPosition(null);
            this.bee = null;
            return true; // successfully removed bee
        } else if (this.swarm != null && this.swarm.removeHornet((Hornet) ins) == true)
        {
            ins.setPosition(null);
            return true;
        } else
        return false;
    }

    public void setOnFire()
    {
        this.onFire = true;
    }

    public boolean isOnFire()
    {
        return this.onFire;
    }


}
