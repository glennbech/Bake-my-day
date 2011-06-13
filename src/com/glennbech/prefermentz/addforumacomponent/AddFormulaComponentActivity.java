package com.glennbech.prefermentz.addforumacomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.model.FormulaComponent;
import com.glennbech.prefermentz.model.Ingredient;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class AddFormulaComponentActivity extends Activity {

    private Ingredient currentIngredient;
    private Formula currentFormula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addformulacomponent);
        ListView lv = (ListView) findViewById(R.id.ingredientList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentIngredient = (Ingredient) adapterView.getItemAtPosition(position);
                TextView tv = (TextView) findViewById(R.id.selectedIngredient);
                tv.setText(currentIngredient.getName());
                View btnSave = findViewById(R.id.btnAddComponent);
                btnSave.setEnabled(true);
            }
        });

        View btnSave = findViewById(R.id.btnAddComponent);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FormulaComponent fc = new FormulaComponent();
                SeekBar sb = (SeekBar) findViewById(R.id.bakerspercentageseekbar);
                fc.setFormula(currentFormula);
                fc.setI(currentIngredient);
                fc.setBp(sb.getProgress() / 100f);
                setResult(RESULT_OK, getIntent().putExtra(FormulaComponent.class.getName(), fc));
                finish();
            }
        });

        View btnCancel = findViewById(R.id.btnCancelAddComponent);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddFormulaComponentActivity.this.finishActivity(RESULT_CANCELED);
            }
        });

        SeekBar sb = (SeekBar) findViewById(R.id.bakerspercentageseekbar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView) findViewById(R.id.bakersPercentage);
                tv.setText(seekBar.getProgress() + "%");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.currentFormula = (Formula) getIntent().getSerializableExtra(Formula.class.getName());
        if (this.currentFormula == null) {
            throw new RuntimeException();
        }
        RecipeOpenHelper oh = new RecipeOpenHelper(this);
        Dao<Ingredient, Integer> dao = oh.getIngredientDao(this);
        try {
            List<Ingredient> ingredientList = dao.queryForAll();
            IngredientListAdapter a = new IngredientListAdapter(this, ingredientList);
            ((ListView) findViewById(R.id.ingredientList)).setAdapter(a);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
