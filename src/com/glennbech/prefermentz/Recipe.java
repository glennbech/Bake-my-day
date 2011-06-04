package com.glennbech.prefermentz;

import java.io.Serializable;

/**
 */

public class Recipe implements Serializable {


    private float flourWeight;
    private float hydration;
    private float fats;
    private float salt;
    private float prefermentPercentage;
    private float preferrmentHydrarion;

    public Recipe(float flourWeight, float hydration, float fats, float salt, float prefermentPercentage, float preferrmentHydrarion) {
        this.flourWeight = flourWeight;
        this.hydration = hydration;
        this.fats = fats;
        this.salt = salt;
        this.prefermentPercentage = prefermentPercentage;
        this.preferrmentHydrarion = preferrmentHydrarion;
    }

    public float getPrefermentWeight() {
        return flourWeight * prefermentPercentage / 100;
    }

    public float getPrefermentWater() {
        return getPrefermentWeight() * preferrmentHydrarion / 100;
    }


    public float getFlourWeight() {
        return flourWeight;
    }

    public void setFlourWeight(float flourWeight) {
        this.flourWeight = flourWeight;
    }

    public float getHydration() {
        return hydration;
    }

    public void setHydration(float hydration) {
        this.hydration = hydration;
    }

    public float getPrefermentPercentage() {
        return prefermentPercentage;
    }

    public void setPrefermentPercentage(float prefermentPercentage) {
        this.prefermentPercentage = prefermentPercentage;
    }

    public float getPreferrmentHydrarion() {
        return preferrmentHydrarion;
    }

    public void setPreferrmentHydrarion(float preferrmentHydrarion) {
        this.preferrmentHydrarion = preferrmentHydrarion;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getSalt() {
        return salt;
    }

    public void setSalt(float salt) {
        this.salt = salt;
    }

    public float getPrefermentLeaven() {
        return flourWeight * 1f / 100;
    }

}
