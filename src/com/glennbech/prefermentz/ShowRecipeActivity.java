package com.glennbech.prefermentz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowRecipeActivity extends Activity {


    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        recipe = (Recipe) getIntent().getExtras().getSerializable(Recipe.class.getName());

        TextView tvPreFl = (TextView) findViewById(R.id.prefermentFlourValue);
        tvPreFl.setText(Float.toString(recipe.getPrefermentWeight() - recipe.getPrefermentWater()));

        TextView tvPreLiq = (TextView) findViewById(R.id.prefermentWaterValue);
        tvPreLiq.setText(Float.toString(recipe.getPrefermentWater()));

        TextView tvYeast = (TextView) findViewById(R.id.prefermentLeavenValue);
        tvYeast.setText(Float.toString(recipe.getPrefermentLeaven()));


        TextView tvDoughFlour = (TextView) findViewById(R.id.doughFlourValue);
        tvDoughFlour.setText(Float.toString(recipe.getFlourWeight() - (recipe.getPrefermentWeight() - recipe.getPrefermentWater())));

        TextView tvDoughWater = (TextView) findViewById(R.id.douhgLiquidValue);
        tvDoughWater.setText(Float.toString((recipe.getHydration() * recipe.getFlourWeight() / 100) - recipe.getPrefermentWater()));

        TextView tvDoughSalt = (TextView) findViewById(R.id.doughSaltValue);
        tvDoughSalt.setText(Float.toString((recipe.getFlourWeight() * recipe.getSalt() / 100f)));

        TextView tvDoughtFat = (TextView) findViewById(R.id.doughFatValue);
        tvDoughtFat.setText(Float.toString((recipe.getFlourWeight() * recipe.getFats() / 100)));

    }


}
