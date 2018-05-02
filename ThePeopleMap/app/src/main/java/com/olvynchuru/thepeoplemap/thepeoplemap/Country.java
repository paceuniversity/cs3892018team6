package com.olvynchuru.thepeoplemap.thepeoplemap;

/**
 * Created by Luridel on 3/19/2018.
 */

public class Country {

    public static int numCountries = 0;

    private String name;

    private int population;

    public Country() {
    }

    public Country(String name) {
        numCountries++;
        this.name = name;
    }

    public Country(String name, int population) {
        numCountries++;
        this.name = name;
        this.population = population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return this.population;
    }

    public String getName() {
        return this.name;
    }
}
