package com.glennbech.prefermentz.newformula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.formuladetails.FormulaDetailsActivity;
import com.glennbech.prefermentz.model.Formula;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * @author Glenn Bech
 */
public class NewFormulaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createformula);

        final EditText e = (EditText) findViewById(R.id.createformulaname);
        View ok = findViewById(R.id.btnsavenewformula);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Formula f = new Formula();
                f.setName(e.getText().toString());
                RecipeOpenHelper r = new RecipeOpenHelper(NewFormulaActivity.this);
                Dao<Formula, String> dao = r.getFormalaDao(NewFormulaActivity.this);
                try {
                    dao.create(f);
                    Intent intent = new Intent().setClass(NewFormulaActivity.this, FormulaDetailsActivity.class);
                    intent.putExtra(Formula.class.getName(), f);
                    startActivity(intent);
                } catch (SQLException e1) {
                    throw new RuntimeException();
                }
            }
        });

        View cancel = findViewById(R.id.btncancelformula);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
