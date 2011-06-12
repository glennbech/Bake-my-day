package com.glennbech.prefermentz.formulalist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.glennbech.prefermentz.formuladetails.FormulaActivity;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class FormulaListActivity extends OrmLiteBaseActivity<RecipeOpenHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.formulalistlayout);

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

        ListView lv = (ListView) findViewById(R.id.formulalist);
        lv.setAdapter(new FormulaListAdapter(this, formulas));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Formula f = (Formula) adapterView.getItemAtPosition(i);
                Intent intent = new Intent().setClass(FormulaListActivity.this, FormulaActivity.class);
                intent.putExtra(Formula.class.getName(), (Serializable) f);
                startActivity(intent);
            }
        });
    }

}
