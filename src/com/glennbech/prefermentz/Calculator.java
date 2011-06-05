package com.glennbech.prefermentz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import java.text.DecimalFormat;


/**
 * @author Glenn Bech
 */
public class Calculator extends Activity {

    private Recipe recipe;
    private float ozFactor = 28.3495231f;
    private static DecimalFormat f = new DecimalFormat("###.#");

    GoogleAnalyticsTracker tracker;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start("UA-23789697-1", this);

        recipe = new Recipe(1000, 65, 3, 2, 1f);

        final SeekBar sbFlour = (SeekBar) findViewById(R.id.sbFlourWegiht);
        final SeekBar sbHydration = (SeekBar) findViewById(R.id.sbHydration);
        final SeekBar sbPrefermentPercentage = (SeekBar) findViewById(R.id.sbPrefermentPercentage);
        final SeekBar sbSalt = (SeekBar) findViewById(R.id.sbSaltWeight);
        final SeekBar sbFats = (SeekBar) findViewById(R.id.sbFats);
        final SeekBar sbYeast = (SeekBar) findViewById(R.id.sbYeastWeight);

        Spinner s = (Spinner) findViewById(R.id.prefermentTypeSpinner);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onResume();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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
                    recipe.setFlourWeight(3000 * (progress / 100f));
                } else if (seekBar == sbHydration) {
                    recipe.setBpWater(50f + (progress / 100f * 30f));
                } else if (seekBar == sbPrefermentPercentage) {
                    recipe.setFlourWeightPreferment(progress * recipe.getFlourWeight() / 100f);
                } else if (seekBar == sbSalt) {
                    recipe.setBpSalt(progress * 5f / 100);
                } else if (seekBar == sbFats) {
                    recipe.setBpFats(progress);
                } else if (seekBar == sbYeast) {
                    recipe.setBpYeast(5f * progress / 100f);
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
        sbYeast.setOnSeekBarChangeListener(onSeekBarChangeListener);

        Spinner spinner = (Spinner) findViewById(R.id.prefermentTypeSpinner);
        String[] values = new String[]{"No Preferment", "Poolish"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.muConfig) {
            Intent i = new Intent(this, PreferenceActivity.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        tracker.trackPageView("/activity/Calculator");

        boolean useMetric = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("metric", true);
        String unitName = useMetric ? " g" : " oz";

        // flour weight
        TextView tvFlour = (TextView) findViewById(R.id.flourWeightValue);
        tvFlour.setText(f.format(recipe.getFlourWeight() / (useMetric ? 1 : ozFactor)) + unitName);

        // hydration
        TextView tvHydration = (TextView) findViewById(R.id.hydrationValue);
        tvHydration.setText(f.format(recipe.getBpWater()) + "%");

        // preferment percentage
        TextView tvPrefermentPercentage = (TextView) findViewById(R.id.prefermentPercentageValue);
        tvPrefermentPercentage.setText(f.format(recipe.getFlourWeightPreferment() / recipe.getFlourWeight() * 100) + "%");

        // salt
        TextView tvSalt = (TextView) findViewById(R.id.saltWeightValue);
        tvSalt.setText(f.format(recipe.getBpSalt()) + "%");

        // fats
        TextView tvFats = (TextView) findViewById(R.id.fatWeightValue);
        tvFats.setText(f.format(recipe.getBpFats()) + "%");

        // yeast
        TextView tvYeast = (TextView) findViewById(R.id.yeastWeightValue);
        tvYeast.setText(f.format(recipe.getBpYeast()) + "%");

        Spinner prefermentSpinner = (Spinner) findViewById(R.id.prefermentTypeSpinner);
        String prfermentType = (String) prefermentSpinner.getAdapter().getItem(prefermentSpinner.getSelectedItemPosition());

        View v = findViewById(R.id.sbPrefermentPercentage);
        if (prfermentType.toLowerCase().equals("no preferment")) {
            v.setEnabled(false);
            recipe.setFlourWeightPreferment(0);
        } else {
            v.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tracker.stop();
    }
}
