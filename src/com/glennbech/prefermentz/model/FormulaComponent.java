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
public class FormulaComponent implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true)
    private Formula formula;
    @DatabaseField(foreign = true)
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

    public void loadIngredient(Context context) {
        try {
            RecipeOpenHelper helper = new RecipeOpenHelper(context);
            Dao<Ingredient, Integer> fclDao = helper.getIngredientDao(context);
            List<Ingredient> ingredientList = fclDao.queryForAll();
            for (Ingredient i : ingredientList) {
                Log.d(FormulaComponent.class.getName(), i.getId() + " " + i.getName());
            }

            Dao<FormulaComponent, Integer> fclDao2 = helper.getFormulaComponentDao(context);
            List<FormulaComponent> compList = fclDao2.queryForAll();
            for (FormulaComponent c : compList) {
                Log.d(FormulaComponent.class.getName(), c.getId() + " " + c.getBp() + " " + c.getI());
            }


            Ingredient i = fclDao.queryForId(getI().getId());
            setI(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "FormulaComponent{" +
                "i=" + i +
                ", bp=" + bp +
                '}';
    }

}

