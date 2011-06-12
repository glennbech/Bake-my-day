package com.glennbech.prefermentz.formulalist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.formuladetails.FormulaActivity;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.model.Recipe;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.glennbech.prefermentz.recipe.RecipeActivity;
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
        ListView lv = (ListView) findViewById(R.id.formulaList);
        registerForContextMenu(lv);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        ListView lv = (ListView) findViewById(R.id.formulaList);
        lv.setAdapter(new FormulaListAdapter(this, formulas));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Formula f = (Formula) adapterView.getItemAtPosition(i);
                Intent intent = new Intent().setClass(FormulaListActivity.this, RecipeActivity.class);
                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(FormulaListActivity.this);
                float defaultWeight = p.getFloat("defaultWeight", 1000f);
                Recipe r = new Recipe(f, defaultWeight);
                intent.putExtra(Recipe.class.getName(), (Serializable) r);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater milf = new MenuInflater(this);
        milf.inflate(R.menu.longpressmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int arrayAdapterPosition = menuInfo.position;

        if (item.getItemId() == R.id.editforumla) {
            ListView lv = (ListView) findViewById(R.id.formulaList);
            ArrayAdapter<Formula> adapter = (ArrayAdapter<Formula>) lv.getAdapter();
            Formula f = (Formula) adapter.getItem(arrayAdapterPosition);
            Intent i = new Intent().setClass(FormulaListActivity.this, FormulaActivity.class);
            i.putExtra(Formula.class.getName(), f);
            startActivity(i);
        }

        return true;
    }
}