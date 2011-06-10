package com.glennbech.prefermentz;

import android.os.Bundle;
import android.widget.ListView;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.persistance.RecipeOpenHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class FormulaListActivity extends OrmLiteBaseActivity<RecipeOpenHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredientlist);

        List<Formula> formulas;

        try {
            Dao dao = getHelper().getDao(Formula.class);
            formulas = dao.queryForAll();

            for (Formula f : formulas) {
                f.loadFormulaComponents(this);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ListView lv = (ListView) findViewById(R.id.ingredientList);
        lv.setAdapter(new FormulaListAdapter(this, formulas));
    }

}
