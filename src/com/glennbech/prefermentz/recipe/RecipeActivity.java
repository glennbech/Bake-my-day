package com.glennbech.prefermentz.recipe;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.Recipe;
import com.glennbech.prefermentz.model.RecipeComponent;

import java.util.Collections;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class RecipeActivity extends Activity {

    public static final float MAX_FLOUR = 3000f;
    private Recipe recipe;
    private RecipeComponentListAdapter recipeComponentListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        final TextView flourWeightText = (TextView) findViewById(R.id.flourweighttext);
        SeekBar sbFlour = (SeekBar) findViewById(R.id.flourWeight);
        sbFlour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float w = i / 100f * MAX_FLOUR;
                flourWeightText.setText((int) w + "g");
                recipe.setTotalFlourWeight(w);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //todo fix this with adapter.invalidate.
                recipeComponentListAdapter = new RecipeComponentListAdapter(RecipeActivity.this, recipe.getRecipeComponents(RecipeActivity.this));
                ListView lv = (ListView) findViewById(R.id.recipeingredientlist);
                lv.setAdapter(recipeComponentListAdapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        this.recipe = (Recipe) i.getSerializableExtra(Recipe.class.getName());
        if (this.recipe == null) {
            throw new RuntimeException();
        }

        TextView tv = (TextView) findViewById(R.id.recipeTitle);
        tv.setText(recipe.getFormula().getName());

        List<RecipeComponent> recipeComponents = recipe.getRecipeComponents(RecipeActivity.this);
        Collections.sort(recipeComponents);
        recipeComponentListAdapter = new RecipeComponentListAdapter(this, recipeComponents);
        SeekBar sbFlour = (SeekBar) findViewById(R.id.flourWeight);
        sbFlour.setProgress((int) (100f * recipe.getTotalFlourWeight() / MAX_FLOUR));
        ListView lv = (ListView) findViewById(R.id.recipeingredientlist);
        lv.setAdapter(recipeComponentListAdapter);
    }


}
