import java.util.Arrays;

public class Plant {
    private String species;
    private int height;

    public Plant(String s, int h) {
        this.species = s;
        this.height = h;
    }

    public void giveWater() {
        this.height += 5;
    }

    public static void waterThePlants(Plant[] toWater) {
        for (Plant p : toWater)
            p.giveWater();
        System.out.print(Arrays.toString(toWater));
    }

    public String toString()
    {
        return this.species + " " + this.height;
    }

    public static void main(String[] args)
    {
        Plant oak = new Plant("Oak", 10);
        Plant olive = new Plant("Olive", 7);
        Plant[] plants = {oak, olive};
        Plant[] toWater = new Plant[plants.length];
        for (int i=0; i<plants.length; i++) {
            toWater[i] = plants[i];
        }
        Plant.waterThePlants(toWater);

    }
}