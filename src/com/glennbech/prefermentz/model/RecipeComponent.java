package com.glennbech.prefermentz.model;

/**
 * @author Glenn Bech
 */
public class RecipeComponent {

    private Ingredient i;
    private float weight;

    public RecipeComponent(Ingredient i, float weight) {
        this.i = i;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RecipeComponent{" +
                "i=" + i +
                ", weight=" + weight +
                '}';
    }
}

