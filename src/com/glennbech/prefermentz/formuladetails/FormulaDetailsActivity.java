package com.glennbech.prefermentz.formuladetails;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.addforumacomponent.AddFormulaComponentActivity;
import com.glennbech.prefermentz.formulalist.FormulaListActivity;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.model.FormulaComponent;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;

/**
 * @author Glenn Bech
 */
public class FormulaDetailsActivity extends Activity {

    private float ozFactor = 28.3495231f;
    private static DecimalFormat f = new DecimalFormat("###.#");

    private Formula formula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulacomponentlayout);
        View cancelButton = findViewById(R.id.btnCancelFormula);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent().setClass(FormulaDetailsActivity.this, FormulaListActivity.class));
            }
        });

        View saveButton = findViewById(R.id.btnSaveFormula);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                save();
            }
        });

        View addbutton = findViewById(R.id.btnAddIngredientToFormula);
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(FormulaDetailsActivity.this, AddFormulaComponentActivity.class);
                i.putExtra(Formula.class.getName(), formula);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FormulaComponent comp = (FormulaComponent) data.getSerializableExtra(FormulaComponent.class.getName());
            formula.getFormulaComponentList().add(comp);
            Collections.sort(formula.getFormulaComponentList());
            ArrayAdapter adapter = (ArrayAdapter) ((ListView) findViewById(R.id.lvFormula)).getAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        this.formula = (Formula) i.getSerializableExtra(Formula.class.getName());
        if (this.formula == null) {
            throw new RuntimeException("need formula as serializable Extra");
        }

        TextView tv = (TextView) findViewById(R.id.formulaName);
        tv.setText(formula.getName());

        boolean useMetric = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("metric", true);
        String unitName = useMetric ? " g" : " oz";

        ListView lv = (ListView) findViewById(R.id.lvFormula);
        lv.setAdapter(new FormulaComponentListAdapter(this, formula.getFormulaComponentList()));
    }

    public void save() {
        AsyncTask at = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... objects) {
                RecipeOpenHelper openHelper = new RecipeOpenHelper(FormulaDetailsActivity.this);
                Dao dao = openHelper.getFormulaComponentDao(FormulaDetailsActivity.this);
                try {
                    for (FormulaComponent currentComponent : formula.getFormulaComponentList()) {
                        if (currentComponent.getId() == -1) {
                            dao.create(currentComponent);
                        }
                        dao.update(currentComponent);
                    }
                } catch (SQLException e) {
                    Toast.makeText(FormulaDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                Toast.makeText(FormulaDetailsActivity.this, "Saved", Toast.LENGTH_LONG).show();
                startActivity(new Intent().setClass(FormulaDetailsActivity.this, FormulaListActivity.class));
            }
        };
        at.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
