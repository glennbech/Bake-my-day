package com.glennbech.prefermentz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author Glenn Bech
 */
@DatabaseTable(tableName = "ingredient")
public class Ingredient implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField()
    private String name;
    @DatabaseField()
    private boolean flour;

    public Ingredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient(String name, boolean flour) {
        this.name = name;
        this.flour = flour;
    }

    public Ingredient(String name) {
        this.name = name;
        this.flour = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlour() {
        return flour;
    }

    public void setFlour(boolean flour) {
        this.flour = flour;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", flour=" + flour +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
