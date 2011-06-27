package com.glennbech.prefermentz.recipe;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
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
            Typeface font = Typeface.createFromAsset(getAssets(), "ChopinScript.otf");
        ((TextView) findViewById(R.id.recipeTitle)).setTypeface(font);

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
        ListView lv = (ListView) findViewById(R.id.recipeingredientlist);
        lv.setAdapter(recipeComponentListAdapter);
    }


}
