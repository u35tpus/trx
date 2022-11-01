package com.ol.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppTest {
    @Test
    public void runHardcodedTests() {
        List<TestDatum> tests = new ArrayList<>();

        tests.add(new TestDatum(new int[]{5, 2, 3, 4, 5, 4, 0, 3, 1}, true, 9, "task"));

        int[] h = new int[Landscape.LIMIT + 1];
        tests.add(new TestDatum(h, false, 0, "test len Limit"));

        tests.stream().forEach(e -> runTest(e));
    }

    @Test
    public void runResourceTests() {
        int valids = 4;
        int invalids = 2;

        List<TestDatum> tests = new ArrayList<>();

        for (int i = 1; i <= valids; i++) {
            String path = "valids/t" + i;
            String f = readResourceFile(path);
            TestDatum d = parseValidFile(f, path);
            tests.add(d);
        }

        for (int i = 1; i <= invalids; i++) {
            String path = "invalids/t" + i;
            String f = readResourceFile(path);
            TestDatum d = parseInvalidFile(f.trim(), path);
            tests.add(d);
        }
        tests.stream().forEach(e -> runTest(e));
    }

    public TestDatum parseInvalidFile(String contents, String name) {
        String[] nums = contents.split(",");
        int[] heights = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            heights[i] = Integer.parseInt(nums[i]);
        }

        return new TestDatum(heights, false, 0, name);
    }

    String readResourceFile(String p) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(p).getFile());
            InputStream inputStream = new FileInputStream(file);

            String text = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            return text;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public TestDatum parseValidFile(String f, String name) {
        String[] lines = f.split("\n");
        int maxHeight = lines.length;
        int len = lines[0].length();
        int[] heights = new int[len];
        int amountWater = 0;
        for (int i = 0; i < len; i++) {
            int k = 0;
            for (int j = 0; j < maxHeight; j++) {
                if (lines[j].charAt(i) == 'x') {
                    k++;
                }
                if (lines[j].charAt(i) == 'w') {
                    amountWater++;
                }
            }
            heights[i] = k;
        }
        return new TestDatum(heights, true, amountWater, name);
    }

    public void runTest(TestDatum testDatum) {
        Landscape landscape = new Landscape(testDatum.getInput());

        landscape.tightAndFill();

        System.out.println("Running " + testDatum.getName());
        Assert.assertEquals(testDatum.isValid(), landscape.isValid());

        if (landscape.isValid()) {
            int cnt = landscape.calcWater();
            Assert.assertEquals(testDatum.getAmountWater(), cnt);
        }
    }
}
