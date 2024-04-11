package org.cteichert.server.utils;

import java.util.Random;

public final class Maths {
	private Maths() {}
	
	public static int getRandomNumberInRange(int min, int max) {
        if (min == max) {
            return min;
        }
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min!");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int getNumberOfDecimalPlaces(double number) {
        String text = Double.toString(Math.abs(number));
        int integerPlaces = text.indexOf('.');
        return text.length() - integerPlaces - 1;
    }

    public static double convertTimeInSecondsToMillis(double number) {
	    float secondsInMillis = 1000;
	    int decimalPlaces = getNumberOfDecimalPlaces(number);

	    if (decimalPlaces > 3) {
	        return Double.parseDouble(getZerosByDecimalPlaces(decimalPlaces-3) + ".1");
        } else if (decimalPlaces > 0) {
            return Double.parseDouble("1" + getZerosByDecimalPlaces(3-decimalPlaces));
        }

	    return secondsInMillis;
    }

    private static String getZerosByDecimalPlaces(int decimalPlaces) {
        return "0".repeat(Math.max(0, decimalPlaces));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
