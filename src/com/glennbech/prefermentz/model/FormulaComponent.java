package com.glennbech.prefermentz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * @author Glenn Bech
 */

@DatabaseTable(tableName = "formulacomponent")
public class FormulaComponent {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true)
    private Formula formula;
    @DatabaseField(foreign = true)
    Ingredient i;
    @DatabaseField
    float bp;

    public FormulaComponent() {
    }

    public FormulaComponent(Formula formula, Ingredient i, float bp) {
        this.formula = formula;
        this.i = i;
        this.bp = bp;
    }

    public Ingredient getI() {
        return i;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setI(Ingredient i) {
        this.i = i;
    }

    public float getBp() {
        return bp;
    }

    public void setBp(float bp) {
        this.bp = bp;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FormulaComponent{" +
                "i=" + i +
                ", bp=" + bp +
                '}';
    }

}

