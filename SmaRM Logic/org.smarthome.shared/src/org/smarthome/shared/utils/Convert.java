package org.smarthome.shared.utils;

public class Convert {
    
    public static double mphToKmh(double mph) {
    	return mph * 1.609344;
    }
    
    public static double fahrenheitToCentigrate(double fahrenheit) {
    	return (fahrenheit - 32) * 5 / 9; 
    }
    
}
