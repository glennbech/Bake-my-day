package com.glennbech.prefermentz.model;

/**
 * @author Glenn Bech
 */
public class RecipeComponent implements Comparable<RecipeComponent> {

    private Ingredient i;
    private float weight;
    private FormulaComponent fc;

    public RecipeComponent(FormulaComponent fc, Ingredient i, float weight) {
        this.i = i;
        this.weight = weight;
        this.fc = fc;
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

    public FormulaComponent getFc() {
        return fc;
    }

    public void setFc(FormulaComponent fc) {
        this.fc = fc;
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

