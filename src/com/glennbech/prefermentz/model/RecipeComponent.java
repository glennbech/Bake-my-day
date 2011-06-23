package com.glennbech.prefermentz.model;

/**
 * @author Glenn Bech
 */
public class RecipeComponent implements Comparable<RecipeComponent> {

    private Ingredient i;
    private float weight;

    public RecipeComponent(Ingredient i, float weight) {
        this.i = i;
        this.weight = weight;
    }


    public Ingredient getI() {
        return i;
    }

    public void setI(Ingredient i) {
        this.i = i;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "RecipeComponent{" +
                "i=" + i +
                ", weight=" + weight +
                '}';
    }


    public int compareTo(RecipeComponent o) {
        if (o == null) {
            throw new RuntimeException();
        }
        if (o.getWeight() == getWeight()) {
            return 0;
        }
        return o.getWeight() > getWeight() ? 1 : -1;
    }
}

