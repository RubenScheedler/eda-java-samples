package com.example;

import java.util.List;
import java.util.ArrayList;

/**
 * Example class for demonstration purposes.
 * Contains simple methods that can be used as code samples.
 */
public class Example {
    
    private String name;
    
    public Example(String name) {
        this.name = name;
    }
    
    /**
     * Simple greeting method.
     * @return A greeting message
     */
    public String greet() {
        return "Hello, " + name + "!";
    }
    
    /**
     * Adds two integers.
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Calculates the factorial of a number.
     * @param n The number to calculate factorial for (must be non-negative)
     * @return The factorial of n
     * @throws IllegalArgumentException if n is negative
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
    
    /**
     * Checks if a string is a palindrome.
     * @param str The string to check
     * @return true if the string is a palindrome, false otherwise
     */
    public boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        String cleaned = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }
    
    /**
     * Filters even numbers from a list.
     * @param numbers List of integers
     * @return List containing only even numbers
     */
    public List<Integer> filterEvenNumbers(List<Integer> numbers) {
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (number != null && number % 2 == 0) {
                evenNumbers.add(number);
            }
        }
        return evenNumbers;
    }
    
    /**
     * Gets the name associated with this Example instance.
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name for this Example instance.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
}