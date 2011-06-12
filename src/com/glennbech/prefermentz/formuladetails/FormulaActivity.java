package com.glennbech.prefermentz.formuladetails;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.formulalist.FormulaListActivity;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.model.FormulaComponent;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * @author Glenn Bech
 */
public class FormulaActivity extends Activity {

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
                startActivity(new Intent().setClass(FormulaActivity.this, FormulaListActivity.class));
            }
        });

        View saveButton = findViewById(R.id.btnSaveFormula);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                save();
            }
        });
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
        formula.loadFormulaComponents(this);
        lv.setAdapter(new FormulaComponentListAdapter(this, formula.getFormulaComponentList()));
    }


    public void save() {
        AsyncTask at = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... objects) {
                RecipeOpenHelper openHelper = new RecipeOpenHelper(FormulaActivity.this);
                Dao dao = openHelper.getFormulaComponentDao(FormulaActivity.this);
                try {
                    for (FormulaComponent currentComponent : formula.getFormulaComponentList()) {
                        dao.update(currentComponent);
                    }
                } catch (SQLException e) {
                    Toast.makeText(FormulaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
        at.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
