package com.glennbech.prefermentz.model;

import android.content.Context;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glenn Bech
 */
@DatabaseTable(tableName = "formula")
public class Formula implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;

    private List<FormulaComponent> formulaComponentList = new ArrayList<FormulaComponent>();

    public Formula() {
    }

    public Formula(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FormulaComponent> getFormulaComponentList() {
        return formulaComponentList;
    }

    public void setFormulaComponentList(List<FormulaComponent> formulaComponentList) {
        this.formulaComponentList = formulaComponentList;
    }

    public void addFlour(Ingredient i, float bp) {
        if (!i.isFlour()) {
            throw new RuntimeException();
        }
        add(i, bp);
    }

    public void add(Ingredient i, float bp) {
        if (bp > 1.0) {
            throw new RuntimeException("Please give bps betwen 0 and 1");
        }
        formulaComponentList.add(new FormulaComponent(this, i, bp));
    }

    @Override
    public String toString() {
        return "Formula{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formulaComponentList=" + formulaComponentList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Formula)) return false;
        Formula formula = (Formula) o;
        if (id != formula.id) return false;
        return true;
    }


    @Override
    public int hashCode() {
        return id;
    }

    public void loadFormulaComponents(Context context) {
        try {
            RecipeOpenHelper helper = new RecipeOpenHelper(context);
            Dao<FormulaComponent, Integer> fclDao = helper.getFormulaComponentDao(context);
            this.formulaComponentList = fclDao.queryForEq("formula_id", getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
