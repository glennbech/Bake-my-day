package com.glennbech.prefermentz;

import java.io.Serializable;

/**
 */

public class Recipe implements Serializable {

    private float flourWeight;
    private float bpWater;
    private float bpFats;
    private float bpSalt;
    private float bpYeast;

    private float flourWeightPreferment;
    private float bpSaltPreferment;
    private float bpWaterPreferment;
    private float bpYeastPreferment;

    public Recipe(float flourWeight, float bpWater, float bpFats, float bpSalt, float bpYeast) {
        this.flourWeight = flourWeight;
        this.bpWater = bpWater;
        this.bpFats = bpFats;
        this.bpSalt = bpSalt;
        this.bpYeast = bpYeast;
    }

    public Recipe(float flourWeight, float bpWater, float bpFats, float bpSalt, float bpYeast, float flourWeightPreferment, float bpSaltPreferment, float bpWaterPreferment, float bpYeastPreferment) {
        this.flourWeight = flourWeight;
        this.bpWater = bpWater;
        this.bpFats = bpFats;
        this.bpSalt = bpSalt;
        this.bpYeast = bpYeast;
        this.flourWeightPreferment = flourWeightPreferment;
        this.bpSaltPreferment = bpSaltPreferment;
        this.bpWaterPreferment = bpWaterPreferment;
        this.bpYeastPreferment = bpYeastPreferment;
    }

    public float getFlourWeight() {
        return flourWeight;
    }

    public void setFlourWeight(float flourWeight) {
        this.flourWeight = flourWeight;
    }

    public float getBpWater() {
        return bpWater;
    }

    public void setBpWater(float bpWater) {
        this.bpWater = bpWater;
    }

    public float getBpFats() {
        return bpFats;
    }

    public void setBpFats(float bpFats) {
        this.bpFats = bpFats;
    }

    public float getBpSalt() {
        return bpSalt;
    }

    public void setBpSalt(float bpSalt) {
        this.bpSalt = bpSalt;
    }

    public float getBpYeast() {
        return bpYeast;
    }

    public void setBpYeast(float bpYeast) {
        this.bpYeast = bpYeast;
    }

    public float getFlourWeightPreferment() {
        return flourWeightPreferment;
    }

    public void setFlourWeightPreferment(float flourWeightPreferment) {
        this.flourWeightPreferment = flourWeightPreferment;
    }

    public float getBpSaltPreferment() {
        return bpSaltPreferment;
    }

    public void setBpSaltPreferment(float bpSaltPreferment) {
        this.bpSaltPreferment = bpSaltPreferment;
    }

    public float getBpWaterPreferment() {
        return bpWaterPreferment;
    }

    public void setBpWaterPreferment(float bpWaterPreferment) {
        this.bpWaterPreferment = bpWaterPreferment;
    }

    public float getBpYeastPreferment() {
        return bpYeastPreferment;
    }

    public void setBpYeastPreferment(float bpYeastPreferment) {
        this.bpYeastPreferment = bpYeastPreferment;
    }
}

