package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Unit tests for the Example class.
 * Demonstrates various JUnit 5 testing patterns.
 */
@DisplayName("Example Class Tests")
class ExampleTest {
    
    private Example example;
    
    @BeforeEach
    void setUp() {
        example = new Example("World");
    }
    
    @Test
    @DisplayName("Should create Example with correct name")
    void testConstructor() {
        Example testExample = new Example("Test");
        assertEquals("Test", testExample.getName());
    }
    
    @Test
    @DisplayName("Should return proper greeting message")
    void testGreet() {
        String greeting = example.greet();
        assertEquals("Hello, World!", greeting);
    }
    
    @Test
    @DisplayName("Should change name and reflect in greeting")
    void testSetName() {
        example.setName("Java");
        assertEquals("Hello, Java!", example.greet());
    }
    
    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {
        
        @Test
        @DisplayName("Should add positive numbers correctly")
        void testAddPositiveNumbers() {
            assertEquals(5, example.add(2, 3));
            assertEquals(10, example.add(7, 3));
        }
        
        @Test
        @DisplayName("Should add negative numbers correctly")
        void testAddNegativeNumbers() {
            assertEquals(-5, example.add(-2, -3));
            assertEquals(0, example.add(-5, 5));
        }
        
        @Test
        @DisplayName("Should handle zero correctly")
        void testAddWithZero() {
            assertEquals(5, example.add(5, 0));
            assertEquals(0, example.add(0, 0));
        }
    }
    
    @Nested
    @DisplayName("Factorial Tests")
    class FactorialTests {
        
        @Test
        @DisplayName("Should calculate factorial of small numbers")
        void testFactorialSmallNumbers() {
            assertEquals(1, example.factorial(0));
            assertEquals(1, example.factorial(1));
            assertEquals(2, example.factorial(2));
            assertEquals(6, example.factorial(3));
            assertEquals(24, example.factorial(4));
            assertEquals(120, example.factorial(5));
        }
        
        @Test
        @DisplayName("Should throw exception for negative numbers")
        void testFactorialNegativeNumber() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, 
                () -> example.factorial(-1)
            );
            assertEquals("Factorial is not defined for negative numbers", exception.getMessage());
        }
    }
    
    @Nested
    @DisplayName("Palindrome Tests")
    class PalindromeTests {
        
        @Test
        @DisplayName("Should identify simple palindromes")
        void testSimplePalindromes() {
            assertTrue(example.isPalindrome("racecar"));
            assertTrue(example.isPalindrome("level"));
            assertTrue(example.isPalindrome("a"));
            assertTrue(example.isPalindrome(""));
        }
        
        @Test
        @DisplayName("Should identify non-palindromes")
        void testNonPalindromes() {
            assertFalse(example.isPalindrome("hello"));
            assertFalse(example.isPalindrome("world"));
            assertFalse(example.isPalindrome("java"));
        }
        
        @Test
        @DisplayName("Should handle case insensitive palindromes")
        void testCaseInsensitivePalindromes() {
            assertTrue(example.isPalindrome("Racecar"));
            assertTrue(example.isPalindrome("Level"));
            assertTrue(example.isPalindrome("MADAM"));
        }
        
        @Test
        @DisplayName("Should handle null input")
        void testPalindromeWithNull() {
            assertFalse(example.isPalindrome(null));
        }
    }
    
    @Nested
    @DisplayName("Filter Even Numbers Tests")
    class FilterEvenNumbersTests {
        
        @Test
        @DisplayName("Should filter even numbers from mixed list")
        void testFilterEvenNumbers() {
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            List<Integer> evenNumbers = example.filterEvenNumbers(numbers);
            
            assertEquals(Arrays.asList(2, 4, 6, 8, 10), evenNumbers);
        }
        
        @Test
        @DisplayName("Should return empty list when no even numbers")
        void testFilterEvenNumbersNoEvens() {
            List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9);
            List<Integer> evenNumbers = example.filterEvenNumbers(numbers);
            
            assertTrue(evenNumbers.isEmpty());
        }
        
        @Test
        @DisplayName("Should handle empty list")
        void testFilterEvenNumbersEmptyList() {
            List<Integer> numbers = new ArrayList<>();
            List<Integer> evenNumbers = example.filterEvenNumbers(numbers);
            
            assertTrue(evenNumbers.isEmpty());
        }
        
        @Test
        @DisplayName("Should handle list with null values")
        void testFilterEvenNumbersWithNulls() {
            List<Integer> numbers = Arrays.asList(1, null, 2, null, 3, 4);
            List<Integer> evenNumbers = example.filterEvenNumbers(numbers);
            
            assertEquals(Arrays.asList(2, 4), evenNumbers);
        }
        
        @Test
        @DisplayName("Should include zero as even number")
        void testFilterEvenNumbersWithZero() {
            List<Integer> numbers = Arrays.asList(-2, -1, 0, 1, 2);
            List<Integer> evenNumbers = example.filterEvenNumbers(numbers);
            
            assertEquals(Arrays.asList(-2, 0, 2), evenNumbers);
        }
    }
}