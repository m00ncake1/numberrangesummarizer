package com.imaansayed.numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.*;

/** 
 * Units tests for Solution.java
 * @author Imaan Sayed
 */

class solutionTest {    

// collect() tests-----------------------

Solution solution = new Solution();

@Test
public void testCollect_InvalidNum_Float(){
    assertThrows(IllegalArgumentException.class, 
    () -> solution.collect("1.0, 2.0, 3.0"),
    "Expected IllegalArgumentException for non-integer input (float).");
}

@Test
public void testCollect_InvalidNum_NotNum(){
    
    assertThrows(IllegalArgumentException.class, 
        () -> solution.collect("1, 2, three"),
        "Expected IllegalArgumentException for non-num input");
}

@Test
public void testCollect_InvalidDelim(){
    assertThrows(
        IllegalArgumentException.class, 
        () -> { solution.collect("1; 2; 3");},
        "Expected IllegalArgumentException because of bad delim in utility method");
}

@Test
public void testCollect_EmptyIn(){
    String input = "";
    List<Integer> expected =  Collections.emptyList();

    assertEquals(expected, 
    solution.collect(input),
     "Expected empty list for empty input string.");
}


    
@Test
public void testCollect_ValidNum_NegNums() {
    Collection<Integer> result = solution.collect("-1,-2,-3,5,7");
    Collection<Integer> expected = Arrays.asList(-1, -2, -3, 5, 7);
    assertEquals(expected, result,
                "Expected integer collection like [-1,-2,-3,5,7]");
}

@Test
public void testCollect_ValidNum_SampleInput(){
    String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
    Collection<Integer> expected = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
    Collection<Integer> result = solution.collect(input);
    assertEquals(expected, result,
     "Expected collection of integers.");

}



// numberSummarizer() tests-------------------

@Test
public void testSummarizeCollection_FullRange_Sorted(){
    Collection<Integer> input = Arrays.asList(0,1,2,3,4,5);
    String expect = "0-5";
    String result = solution.summarizeCollection(input);
    assertEquals(expect, result, 
    "Expected full range for sorted input.");
}

@Test
public void testSummarizeCollection_FullRange_Unsorted(){
    Collection<Integer> input = Arrays.asList(5,4,3,2,0,1);
    String result = solution.summarizeCollection(input);
    String expected = "0-5";
    assertEquals(expected, result, 
    "The collect method should convert a comma-separated string into a collection of integers.");
}

public void testSummarizeCollection_AdjacentRanges(){
    Collection<Integer> precond = Arrays.asList(20,21,22,25,26,27);
    String result = solution.summarizeCollection(precond);
    String expect = "20-22,25-27";
    assertEquals(expect, result, 
    "Expected two sequential ranges: 20-22 and 25-27.");
}

@Test
public void testSummarizeCollection_NegRange(){
    Collection<Integer> precond = Arrays.asList(0,3,-5,-6,-7);
    String result = solution.summarizeCollection(precond);
    String expect = "-7--5, 0, 3";
    assertEquals(expect, result, 
    "Expected negative number ranges w/ independant non-range numbers.");
}

// Below tests for handling "atypical" but valid input

@Test
public void testSummarizeCollection_NoRange(){
    Collection<Integer> precond = Arrays.asList(-42,0,42,144,178);
    String result = solution.summarizeCollection(precond);
    String expect = "-42, 0, 42, 144, 178";
    assertEquals(expect, result, 
    "Expected individual numbers with no sequential ranges.");
}

@Test
public void testSummarizeCollection_EmptyInput(){
    Collection<Integer> precond = Arrays.asList();
    String result = solution.summarizeCollection(precond);
    String expect = "Empty input";
    assertEquals(expect, result, 
    "Expected 'Empty input' message for empty input.");
}

@Test
public void testSummarizeCollection_Duplicates_FullList(){

    Collection<Integer> precond = Arrays.asList(42,42,42,42);
    String result = solution.summarizeCollection(precond);
    String expect = "42, 42, 42, 42";
    assertEquals(expect, result,
    "Expected full list of duplicates as individual values."); 
}

@Test
public void testSummarizeCollection_Duplicates_InRangeMiddle(){
    Collection<Integer> precond = Arrays.asList(78,79,79,79,80,81);
    String result = solution.summarizeCollection(precond);
    String expect = "78-81";
    assertEquals(expect, result, 
    "Expected range '78-81' to absorb duplicates within a range");
}

@Test
public void testSummarizeCollection_Duplicates_EndOfList(){
    Collection<Integer> precond = Arrays.asList(78,79,80,85,85,85);
    String result = solution.summarizeCollection(precond);
    String expect = "78-80, 85, 85, 85";
    assertEquals(expect, result, 
    "Expected range '78-80' and individual '85' duplicates.");
}

@Test
public void testSummarizeCollection_Duplicates_AtRangeEnd(){
    Collection<Integer> precond = Arrays.asList(62,65,79,80,81,81,81);
    String result = solution.summarizeCollection(precond);
    String expect = "62, 65, 79-81";
    assertEquals(expect, result, 
    "Expected '79-81' to absorb '81' duplicates");
}

@Test
public void testSummarizeCollection_Duplicates_InRangeStart(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(62, 62, 62, 63, 72, 73, 74);
    String result = solution.summarizeCollection(precond);
    String expect = "62-63, 72-74";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

// Large volume edge cases    
@Test
public void testSummarizeCollection_LargeInput() {
    Collection<Integer> input = new ArrayList<>();
    for (int i = 1; i <= 1000; i++) {
        input.add(i);
    }
    String expected = "1-1000";
    String result = solution.summarizeCollection(input);
    assertEquals(expected, result, "Expected single range '1-1000' for large input.");
}

@Test
public void testSummarizeCollection_LargeInputWithDuplicates() {
    Collection<Integer> input = new ArrayList<>();
    for (int i = 1; i <= 1000; i++) {
        input.add(i);
        if (i % 2 == 0) input.add(i); // Add duplicates for even numbers
    }
    String expected = "1-1000"; // Since all even numbers are duplicated
    String result = solution.summarizeCollection(input);
    assertEquals(expected, result, "Expected '1-1000' as the range for input with duplicates.");
}
}
