package com.glennbech.prefermentz.formulalist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.formuladetails.FormulaDetailsActivity;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.model.Recipe;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.glennbech.prefermentz.recipe.RecipeActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class FormulaListActivity extends OrmLiteBaseActivity<RecipeOpenHelper> {


    private RecipeOpenHelper roHelper;
    private FormulaListAdapter formulaListAdapter;
    List<Formula> formulas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulalistlayout);
        ListView lv = (ListView) findViewById(R.id.formulaList);
        registerForContextMenu(lv);

        roHelper = new RecipeOpenHelper(this);

        View v = findViewById(R.id.newformulabutton);
        v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Context c = FormulaListActivity.this;
                final EditText recipeName = new EditText(c);
                final AlertDialog alert = new AlertDialog.Builder(FormulaListActivity.this).create();
                alert.setTitle("New Formula name");
                LinearLayout linearLayout = new LinearLayout(FormulaListActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(recipeName);

                LinearLayout buttons = new LinearLayout(FormulaListActivity.this);
                buttons.setOrientation(LinearLayout.VERTICAL);

                Button okButton = new Button(c);
                okButton.setText("Ok");
                buttons.addView(okButton);
                Button cancelButton = new Button(c);
                cancelButton.setText("Cancel");
                buttons.addView(cancelButton);
                linearLayout.addView(buttons);
                alert.setView(linearLayout);
                alert.show();

                okButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Dao<Formula, String> dao = roHelper.getFormalaDao(FormulaListActivity.this);
                        Formula f = new Formula(recipeName.getText().toString().toLowerCase());
                        try {
                            dao.create(f);
                            Intent intent = new Intent().setClass(FormulaListActivity.this, RecipeActivity.class);
                            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(FormulaListActivity.this);
                            float defaultWeight = p.getFloat("defaultWeight", 1000f);
                            Recipe r = new Recipe(f, defaultWeight);
                            intent.putExtra(Recipe.class.getName(), (Serializable) r);
                            startActivity(intent);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            alert.cancel();
                        }
                    }
                });

            }
        });

        Typeface font = Typeface.createFromAsset(getAssets(), "ChopinScript.otf");
        ((TextView) findViewById(R.id.titlebar)).setTypeface(font);
    }


    protected void onResume() {
        super.onResume();
        refreshList();
        ((ListView) findViewById(R.id.formulaList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void refreshList() {
        try {
            Dao dao = getHelper().getDao(Formula.class);
            formulas = dao.queryForAll();
            for (Formula f : formulas) {
                f.loadFormulaComponents(this);
            }
            Collections.sort(formulas, new Comparator<Formula>() {
                public int compare(Formula o1, Formula o2) {
                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                }
            });
            ListView lv = (ListView) findViewById(R.id.formulaList);
            formulaListAdapter = new FormulaListAdapter(this, formulas);
            lv.setAdapter(formulaListAdapter);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            Intent i = new Intent().setClass(FormulaListActivity.this, FormulaDetailsActivity.class);
            i.putExtra(Formula.class.getName(), f);
            startActivity(i);
        }

        return true;
    }
}