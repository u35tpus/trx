package com.ol.test;

public class Landscape {
    private final int[] heights;
    private boolean valid = true;
    private int tankHeight = 0;
    private int[] checkVectorLeft;
    private int[] checkVectorRight;

    public final static int LIMIT = 32_000;

    public Landscape(int[] heights) {
        this.heights = heights;
    }

    public void tightAndFill() {
        int i = 0;

        if (heights.length > LIMIT) {
            valid = false;
            return;
        }

        for (int l = 0; l < heights.length; l++) {
            if (heights[l] < 0 || heights[l] > LIMIT) {
                valid = false;
                return;
            }
        }

        for (; i < heights.length && heights[i] == 0; i++) ;

        int j = heights.length - 1;
        for (; j > 0 && heights[j] == 0; j--) ;

        for (int s = 0; s < heights.length; s++) {
            if (tankHeight < heights[s]) {
                tankHeight = heights[s];
            }
        }

        if (i >= j) {
            valid = false;
            return;
        }

        checkVectorLeft = new int[heights.length];
        checkVectorRight = new int[heights.length];

        init();
    }

    private void init() {
        int topLeft = heights[0];
        int topRight = heights[heights.length - 1];

        for (int i = 0; i < heights.length ; i++) {
            if (heights[i] > topLeft) {
                topLeft = heights[i];
                checkVectorLeft[i] = heights[i];
            } else {
                checkVectorLeft[i] = topLeft;
            }
        }

        for (int i = heights.length - 1; i >= 0; i--) {
            if (heights[i] > topRight) {
                topRight = heights[i];
                checkVectorRight[i] = heights[i];
            } else {
                checkVectorRight[i] = topRight;
            }
        }
    }

    public int calcWater() {
        int cnt = 0;

        for (int i = 0; i < heights.length; i++) {
            cnt+= Math.min(checkVectorLeft[i], checkVectorRight[i]) - heights[i];
        }

        return cnt;
    }

    public boolean isValid() {
        return valid;
    }
}
