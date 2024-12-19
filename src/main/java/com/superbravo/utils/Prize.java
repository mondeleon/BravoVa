package com.superbravo.utils;

public class Prize {

    public static double toDouble(String prize) {
        return Double.parseDouble(prize.replaceAll("[^0-9.,]", ""));
    }

}
