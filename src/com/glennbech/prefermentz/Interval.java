package com.glennbech.prefermentz;

/**
 * @author Gl
 * enn Bech
 */
public class Interval {

    private float start;
    private float end;
    private String text;

    public Interval(float start, float end, String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
