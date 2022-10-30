package com.ol.test;

public class Landscape {
    private final int[] heights;
    private boolean valid = true;
    private Matter[][] tank;
    private int tankHeight = 0;
    private int tankLength = 0;

    private final static int LIMIT = 32_000;

    public Landscape(int[] heights) {
        this.heights = heights;
    }

    public void tightAndFill() {
        int i = 0;

        for (int l = 0; l < heights.length ; l++) {
            if (heights[l] < 0 || heights[l] > LIMIT) {
                valid = false;
                return;
            }
        }

        for (; i < heights.length && heights[i] == 0; i++);

        int j = heights.length - 1;
        for (; j > 0 && heights[j] == 0; j--);

        for (int s = 0; s < heights.length; s++) {
            if (tankHeight < heights[s]) {
                tankHeight = heights[s];
            }
        }

        if (i >= j) {
            valid = false;
            return;
        }

        tankLength = j - i + 1;

        tank = new Matter[tankLength][tankHeight];
        //0 - water, 1 - stone, 2 - air
        for (int k = i, y = 0; k <= j; k++, y++) {
            tank[y] = new Matter[tankHeight];
            int l = 0;
            for (; l < heights[k]; l++) {
                tank[y][l] = Matter.STONE; //stone
            }
            for (; l < tankHeight; l++) {
                tank[y][l] = Matter.WATER; //stone
            }
        }
    }

    public void spill() {
        for (int level = 0; level < tankHeight; level++) {
            for (int i = 0; i < tankLength; i++) {
                if (tank[i][level] == Matter.WATER) {
                    if (canSpill(i, level)) {
                        tank[i][level] = Matter.AIR;
                    }
                }
            }
        }
    }


    public int calcWater() {
        int cnt = 0;
        for (int level = 0; level < tankHeight; level++) {
            for (int i = 0; i < tankLength; i++) {
                if (tank[i][level] == Matter.WATER) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    private boolean canSpill(int x, int level) {
        //check to the right
        boolean canSpill = true;
        for (int i = x - 1; i >= 0; i--) {
            if (tank[i][level] == Matter.STONE) {
                canSpill = false;
                break;
            }
        }

        if (canSpill) {
            return true;
        }

        //check to the left
        for (int i = x + 1; i < tankLength; i++) {
            if (tank[i][level] == Matter.STONE) {
                return false;
            }
        }

        return true;
    }

    public boolean isValid() {
        return valid;
    }
}
