package com.glennbech.prefermentz.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.model.FormulaComponent;
import com.glennbech.prefermentz.model.Ingredient;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class RecipeOpenHelper extends OrmLiteSqliteOpenHelper {

    private static String databaseName = "bakemyday";
    private List<FormulaComponent> formulaComponentList;

    public RecipeOpenHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.dropTable(connectionSource, Ingredient.class, true);
            TableUtils.createTable(connectionSource, Ingredient.class);

            TableUtils.dropTable(connectionSource, Formula.class, true);
            TableUtils.createTable(connectionSource, Formula.class);

            TableUtils.dropTable(connectionSource, FormulaComponent.class, true);
            TableUtils.createTable(connectionSource, FormulaComponent.class);

            Dao dao = getDao(Ingredient.class);
            Ingredient i;

            i = new Ingredient("Wheat AP flour", true);
            dao.create(i);
            int wheatId = i.getId();

            i = new Ingredient("Rye AP flour", true);
            dao.create(i);
            int ryeId = i.getId();

            i = new Ingredient("Water", false);
            dao.create(i);
            int waterId = i.getId();

            i = new Ingredient("Salt", false);
            dao.create(i);
            int saltId = i.getId();

            i = new Ingredient("Dry yeast", false);
            dao.create(i);
            int yeastId = i.getId();

            Formula f;

            f = new Formula("Simple white bread");
            Dao foDao = getDao(Formula.class);
            foDao.create(f);

            Dao fcDao = getDao(FormulaComponent.class);

            Ingredient flour = (Ingredient) dao.queryForId(wheatId);
            FormulaComponent fcFlour = new FormulaComponent(f, flour, 1f);
            fcDao.create(fcFlour);

            Ingredient water = (Ingredient) dao.queryForId(waterId);
            FormulaComponent fcWater = new FormulaComponent(f, water, 0.65f);
            fcDao.create(fcWater);

            Ingredient salt = (Ingredient) dao.queryForId(saltId);
            FormulaComponent fcSalt = new FormulaComponent(f, salt, .02f);
            fcDao.create(fcSalt);


            Ingredient yeast = (Ingredient) dao.queryForId(yeastId);
            FormulaComponent fcYeast = new FormulaComponent(f, yeast, 0.01f);
            fcDao.create(fcYeast);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Dao<Formula, String> getFormalaDao(Context context) {
        try {
            return getDao(Formula.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    }

    public List<FormulaComponent> getFormulaComponentList() throws SQLException {
        Dao fcDao = getDao(FormulaComponent.class);
        return null;
    }

    /**
     * @param context
     * @return
     */
    public Dao<FormulaComponent, Integer> getFormulaComponentDao(Context context) {
        try {
            return getDao(FormulaComponent.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Ingredient, Integer> getIngredientDao(Context context) {
        try {
            return getDao(Ingredient.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
