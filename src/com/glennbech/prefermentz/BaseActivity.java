package com.glennbech.prefermentz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Base Activity for each screen.
 *
 * @author Glenn Bech
 */

public class BaseActivity extends Activity {


    private String text;
    private float minSliderValue;
    private float maxSliderValue;
    private Interval[] intervals;

    public BaseActivity(String text, float minSliderValue, float maxSliderValue, Interval[] intervals) {
        this.text = text;
        this.minSliderValue = minSliderValue;
        this.maxSliderValue = maxSliderValue;
        this.intervals = intervals;
    }

    private BaseActivity() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView text = (TextView) findViewById(R.id.text);
        SeekBar sb = (SeekBar) findViewById(R.id.seekbar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}
