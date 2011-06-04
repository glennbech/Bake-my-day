package com.glennbech.prefermentz;

/**
 * Created by IntelliJ IDEA.
 * User: glenn
 * Date: 5/21/11
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivityModel implements java.io.Serializable {


    private String text;
    private float minSliderValue;
    private float maxSliderValue;
    private Interval[] intervals;

    public BaseActivityModel(String text, float minSliderValue, float maxSliderValue, Interval[] intervals) {
        this.text = text;
        this.minSliderValue = minSliderValue;
        this.maxSliderValue = maxSliderValue;
        this.intervals = intervals;
    }
}
