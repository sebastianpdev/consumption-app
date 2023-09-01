package com.jspapps.consumptionapp.infrastructure.util;

import java.util.Random;

public class StringUtils {

    private static final String[] STREETS = {"28a#39-63", "40#10-12", "52#5-32"};

    public static String generateStreetRandom() {
        Random random = new Random();
        return STREETS[random.nextInt(STREETS.length)];
    }
}
