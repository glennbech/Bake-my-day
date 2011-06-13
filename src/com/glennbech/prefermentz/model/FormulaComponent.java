package com.glennbech.prefermentz.model;

import android.content.Context;
import android.util.Log;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Glenn Bech
 */

@DatabaseTable(tableName = "formulacomponent")
public class FormulaComponent implements Serializable, Comparable<FormulaComponent> {

    @DatabaseField(generatedId = true)
    private int id = -1;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Formula formula;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Ingredient i;
    @DatabaseField
    private float bp;

    private float minValue = 0;
    private float maxValue = 100;

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

    public int compareTo(FormulaComponent o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (o.getBp() == getBp()) {
            return 0;
        }
        return o.getBp() > getBp() ? 1 : -1;
    }

    @Override
    public String toString() {
        return "FormulaComponent{" +
                "i=" + i +
                ", bp=" + bp +
                '}';
    }

}

