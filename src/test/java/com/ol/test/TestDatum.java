package com.ol.test;

public class TestDatum {
    private int[] input;
    private boolean valid;
    private int amountWater;

    private String name;

    public TestDatum(int[] input, boolean valid, int amountWater, String name) {
        this.input = input;
        this.valid = valid;
        this.amountWater = amountWater;
        this.name = name;
    }

    public int[] getInput() {
        return input;
    }

    public boolean isValid() {
        return valid;
    }

    public int getAmountWater() {
        return amountWater;
    }

    public String getName() {
        return name;
    }
}
