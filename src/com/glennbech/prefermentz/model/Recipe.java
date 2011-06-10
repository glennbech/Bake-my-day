package com.glennbech.prefermentz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glenn Bech
 */
@DatabaseTable(tableName = "recipe")
public class Recipe implements Serializable {

    @DatabaseField(generatedId = true)
    private int id = -1;
    @DatabaseField
    private String name;
    @DatabaseField
    private Formula formula;
    @DatabaseField
    private float totalFlourWeight;

    private List<RecipeComponent> recipeComponents = new ArrayList<RecipeComponent>();

    public Recipe(String name, Formula formula, float totalFlourWeight) {
        this.name = name;
        this.formula = formula;
        this.totalFlourWeight = totalFlourWeight;

        float bpFlourTotal = 0;
        recipeComponents = new ArrayList<RecipeComponent>();
        for (FormulaComponent fComponent : formula.getFormulaComponentList()) {
            if (fComponent.getI().isFlour()) {
                bpFlourTotal += fComponent.getBp();
            }
            recipeComponents.add(new RecipeComponent(fComponent.getI(), totalFlourWeight * fComponent.getBp()));
        }
        if (bpFlourTotal != 1.0) {
            throw new IllegalArgumentException(bpFlourTotal + "% flours. Should add up to 100% " + formula);
        }
    }

    public Recipe(int recipeId) {
        this.id = recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public float getTotalFlourWeight() {
        return totalFlourWeight;
    }

    public void setTotalFlourWeight(float totalFlourWeight) {
        this.totalFlourWeight = totalFlourWeight;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", formula=" + formula +
                ", totalFlourWeight=" + totalFlourWeight +
                ", recipeComponents=" + recipeComponents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class RecipeComponent {

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

}
