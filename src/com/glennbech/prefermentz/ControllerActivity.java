package com.glennbech.prefermentz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 *
 */
public class ControllerActivity extends Activity {

    int currentPage = 1;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        if (currentPage == 1) {
            Intent intent = new Intent(this, BaseActivity.class);

            Interval intervalHard = new Interval(50, 64, "Stiff");
            Interval intervalMedium = new Interval(64, 67, "Standard");
            Interval intervalSoft = new Interval(64, 100, "Rustic");
            Interval[] intervals = new Interval[]{intervalHard, intervalMedium, intervalSoft};

            String text = "Water / Liquid. The softer the dough, the larger iregullar holes. Difficult to handle";
            BaseActivityModel bm = new BaseActivityModel(text, 50f, 75f, intervals);

            intent.putExtra(BaseActivityModel.class.getName(), bm);
            startActivityForResult(intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        if (resultCode == 1) {
            recipe.setBpWater(bundle.getFloat("value"));
            currentPage = 2;
        }
        onResume();
    }
}
