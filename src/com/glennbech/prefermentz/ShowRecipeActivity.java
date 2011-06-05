package com.glennbech.prefermentz;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ShowRecipeActivity extends Activity {

    private Recipe recipe;
    private float ozFactor = 28.3495231f;
    private static DecimalFormat f = new DecimalFormat("###.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipe = (Recipe) getIntent().getExtras().getSerializable(Recipe.class.getName());

        boolean useMetric = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("metric", true);
        String unitName = useMetric ? " g" : " oz";

        final boolean hasPreferment = recipe.getFlourWeightPreferment() != 0;
        if (!hasPreferment) {
            View v = findViewById(R.id.preferment);
            v.setVisibility(View.GONE);
        } else {
            recipe.setBpWaterPreferment(1.07f);
            recipe.setBpYeastPreferment(0.27f);
            recipe.setFlourWeight(recipe.getFlourWeight() - recipe.getFlourWeightPreferment());
        }

        // preferment

        TextView tvPreFl = (TextView) findViewById(R.id.prefermentFlourValue);
        tvPreFl.setText(f.format((recipe.getFlourWeightPreferment() * (useMetric ? 1 : 1 / ozFactor))) + unitName);

        TextView tvPreLiq = (TextView) findViewById(R.id.prefermentWaterValue);
        tvPreLiq.setText(f.format((recipe.getBpWaterPreferment() * (useMetric ? 1 : 1 / ozFactor) * recipe.getFlourWeightPreferment())) + unitName);

        TextView tvYeast = (TextView) findViewById(R.id.prefermentLeavenValue);
        tvYeast.setText(f.format((recipe.getBpYeastPreferment() * (useMetric ? 1 : 1 / ozFactor) * recipe.getFlourWeightPreferment() / 100)) + unitName);

        // dough

        TextView tvDoughFlour = (TextView) findViewById(R.id.doughFlourValue);
        tvDoughFlour.setText(f.format((recipe.getFlourWeight() * (useMetric ? 1 : 1 / ozFactor))) + unitName);

        TextView tvDoughWater = (TextView) findViewById(R.id.douhgLiquidValue);

        float water;
        if (!hasPreferment) {
            water = (recipe.getBpWater() * recipe.getFlourWeight() / 100f);
        } else {
            float totalWaterInRecipe = (recipe.getBpWater() * (recipe.getFlourWeight() + recipe.getFlourWeightPreferment()) / 100f);
            float waterInpreferment = (recipe.getBpWaterPreferment() * recipe.getFlourWeightPreferment());
            water = totalWaterInRecipe - waterInpreferment;
        }
        tvDoughWater.setText(f.format((water * (useMetric ? 1 : 1 / ozFactor))) + unitName);

        TextView tvDoughSalt = (TextView) findViewById(R.id.doughSaltValue);
        tvDoughSalt.setText(f.format(((recipe.getFlourWeight() * recipe.getBpSalt() / 100f * (useMetric ? 1 : 1 / ozFactor)))) + unitName);

        TextView tvDoughtFat = (TextView) findViewById(R.id.doughFatValue);
        tvDoughtFat.setText(f.format(((recipe.getFlourWeight() * recipe.getBpFats() / 100f * (useMetric ? 1 : 1 / ozFactor)))) + unitName);

        TextView tvDoughYeast = (TextView) findViewById(R.id.doughYeastValue);
        tvDoughYeast.setText(f.format(((recipe.getFlourWeight() * recipe.getBpYeast() / 100f * (useMetric ? 1 : 1 / ozFactor)))) + unitName);

        // profile

        TextView tvProfileHydration = (TextView) findViewById(R.id.profileHydration);
        TextView tvProfileFat = (TextView) findViewById(R.id.profileFat);
        TextView tvProfile = (TextView) findViewById(R.id.profileLeaven);

        if (recipe.getBpWater() < 57f && recipe.getBpWater() >= 50f) {
            tvProfileHydration.setText(getResources().getString(R.string.profileStiff));
        } else if (recipe.getBpWater() >= 57f && recipe.getBpWater() <= 65) {
            tvProfileHydration.setText(getResources().getString(R.string.profileStandard));
        } else if (recipe.getBpWater() > 65f && recipe.getBpWater() < 85) {
            tvProfileHydration.setText(getResources().getString(R.string.profileRustic));
        } else if (recipe.getBpWater() > 80) {
            tvProfileHydration.setText(getResources().getString(R.string.profileTooWet));
        } else if (recipe.getBpWater() < 50) {
            tvProfileHydration.setText(getResources().getString(R.string.profileTooDry));
        }

        if (recipe.getBpFats() == 0f) {
            tvProfileFat.setText(getResources().getString(R.string.profileLean));
        } else if (recipe.getBpFats() > 0 && recipe.getBpFats() <= 20f) {
            tvProfileFat.setText(getResources().getString(R.string.profileEnriched));
        } else if (recipe.getBpFats() > 20f) {
            tvProfileFat.setText(getResources().getString(R.string.profileRich));
        }

        if (recipe.getFlourWeightPreferment() == 0) {
            tvProfile.setText(getResources().getString(R.string.profileDirect));
        } else {
            tvProfile.setText(getResources().getString(R.string.profileIndirect));
        }

    }
}