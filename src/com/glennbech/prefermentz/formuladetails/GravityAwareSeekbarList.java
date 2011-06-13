package com.glennbech.prefermentz.formuladetails;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.SeekBar;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Glenn Bech
 */
public class GravityAwareSeekbarList implements SensorEventListener {

    public static final float X_THRESHOLD = 1f;
    private SensorManager mSensorManager;
    private Sensor sensor;
    private Set<SeekBar> seekBars = new HashSet<SeekBar>();
    private Context context;

    private static String TAG = GravityAwareSeekbarList.class.getName();

    public GravityAwareSeekbarList(Context context) {
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] res = sensorEvent.values;
        Log.d(TAG, "az: " + res[0] + " pitch " + res[1] + " roll " + res[2]);
        if (Math.abs(res[0]-19) > X_THRESHOLD) {
            for (SeekBar seekBar : seekBars) {
                seekBar.setProgress(seekBar.getProgress() + ((int) + ((res[0]-19)/2f )));
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void add(SeekBar sb) {
        seekBars.add(sb);
    }

    public void remove(SeekBar sb) {
        seekBars.remove(sb);
    }

    public void kill() {
        mSensorManager.unregisterListener(this);
    }

}
