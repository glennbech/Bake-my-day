package com.glennbech.prefermentz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.*;


/**
 *
 *
 */
public class Calculator extends Activity {

    private Recipe recipe;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        recipe = new Recipe(2000, 65, 5, 2, 30, 50);

        final SeekBar sbFlour = (SeekBar) findViewById(R.id.sbFlourWegiht);
        final SeekBar sbHydration = (SeekBar) findViewById(R.id.sbHydration);
        final SeekBar sbPrefermentPercentage = (SeekBar) findViewById(R.id.sbPrefermentPercentage);
        final SeekBar sbSalt = (SeekBar) findViewById(R.id.sbSaltWeight);
        final SeekBar sbFats = (SeekBar) findViewById(R.id.sbFats);


        Button b = (Button) findViewById(R.id.calculate);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Calculator.this, ShowRecipeActivity.class);
                i.putExtra(Recipe.class.getName(), recipe);
                startActivity(i);
            }
        });


        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (seekBar == sbFlour) {
                    recipe.setFlourWeight(5000 * (progress / 100f));
                } else if (seekBar == sbHydration) {
                    recipe.setHydration(50f + (progress / 100f * 30f));
                } else if (seekBar == sbPrefermentPercentage) {
                    recipe.setPrefermentPercentage(progress);
                } else if (seekBar == sbSalt) {
                    recipe.setSalt(progress * 5f / 100);
                } else if (seekBar == sbFats) {
                    recipe.setFats(progress);
                }
                onResume();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };

        sbFlour.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbHydration.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbPrefermentPercentage.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbSalt.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbFats.setOnSeekBarChangeListener(onSeekBarChangeListener);

        Spinner spinner = (Spinner) findViewById(R.id.prefermentTypeSpinner);
        String[] values = new String[]{ "No Preferment", "Biga", "Patè de fermentèe", "Poolish"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, values));

    }

    @Override
    protected void onResume() {
        super.onResume();

        // flour weight
        TextView tvFlour = (TextView) findViewById(R.id.flourWeightValue);
        tvFlour.setText(Float.toString(recipe.getFlourWeight()));

        // hydration
        TextView tvHydration = (TextView) findViewById(R.id.hydrationValue);
        tvHydration.setText(Float.toString(recipe.getHydration()) + "%");

        // preferment percentage
        TextView tvPrefermentPercentage = (TextView) findViewById(R.id.prefermentPercentageValue);
        tvPrefermentPercentage.setText(Float.toString(recipe.getPrefermentPercentage()) + "%");

        // salt
        TextView tvSalt = (TextView) findViewById(R.id.saltWeightValue);
        tvSalt.setText(Float.toString(recipe.getSalt()));

        // fats
        TextView tvFats = (TextView) findViewById(R.id.fatWeightValue);
        tvFats.setText(Float.toString(recipe.getFats()));

    }
}
