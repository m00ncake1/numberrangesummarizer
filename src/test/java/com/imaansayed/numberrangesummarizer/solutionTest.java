package com.imaansayed.numberrangesummarizer;
// package numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// import src.main.numberrangesummarizer.Solution;

import java.util.*;

class solutionTest {    


// collect() tests-----------------------
Solution solution = new Solution();

@Test
public void testCollect_BadNum_Float(){
    assertThrows(IllegalArgumentException.class, 
    () -> {
        solution.collect("1.0, 2.0, 3.0");
    });
}

@Test
public void testCollect_BadNum_NotNum(){
    
    assertThrows(
        IllegalArgumentException.class, 
        () -> { solution.collect("1, 2, three"); },
        "Expected IllegalArgumentException for non-num input in list");
}

@Test
public void testCollect_BadDelim(){
    assertThrows(
        IllegalArgumentException.class, 
        () -> { solution.collect("1; 2; 3");},
        "Expected IllegalArgumentException because of bad delim in utility method");
}

@Test
public void testCollect_EmptyIn(){
    String input = "";
    List<Integer> output =  Collections.emptyList();

    assertEquals(output, solution.collect(input));
}


    
@Test
public void testCollect_NegIn() {
    // Test input with negative numbers
    Collection<Integer> result = solution.collect("-1,-2,-3,5,7");
    assertEquals(List.of(-1, -2, -3,5,7), result,
                    "Expected collection like[-1,-2,-3,5,7]");
}

@Test
public void testCollect_SampleIn_Pos(){
    String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
    Collection<Integer> expected = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
    Collection<Integer> result = solution.collect(input);
    assertEquals(expected, result, "The collect method should convert a comma-separated string into a collection of integers.");

}



// numberSummarizer() tests-------------------
@Test
public void testSummarizer_FullRange_Sorted(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(0,1,2,3,4,5);
    String result = solution.summarizeCollection(precond);
    String expect = "0-5";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_FullRange_Unsorted(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(5,4,3,2,0,1);
    String result = solution.summarizeCollection(precond);
    String expect = "0-5";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

public void testSummarizer_Pos_AdjRanges(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(20,21,22,25,26,27);
    String result = solution.summarizeCollection(precond);
    String expect = "20-22,25-27";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_Pos_NegRange(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(0,3,-5,-6,-7);
    String result = solution.summarizeCollection(precond);
    String expect = "-7--5, 0, 3";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

// Below tests for handling "atypical" but valid input

@Test
public void testSummarizer_Neg_NoRange(){
    Collection<Integer> precond = Arrays.asList(20,22,24,26,28,30);
    String result = solution.summarizeCollection(precond);
    String expect = "20, 22, 24, 26, 28, 30";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_Neg_EmptyInput(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList();
    String result = solution.summarizeCollection(precond);
    String expect = "Empty input";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_Duplicates_FullList(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(42,42,42,42);
    String result = solution.summarizeCollection(precond);
    String expect = "42, 42, 42, 42";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_Duplicates_InRangeMiddle(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(78,79,79,79,80,81);
    String result = solution.summarizeCollection(precond);
    String expect = "78-81";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_Duplicates_EndOfList(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(78,79,80,85,85,85);
    String result = solution.summarizeCollection(precond);
    String expect = "78-80, 85, 85, 85";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}

@Test
public void testSummarizer_Duplicates_InRangeEnd(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(62,65,79,80,81,81,81);
    String result = solution.summarizeCollection(precond);
    String expect = "62, 65, 79-81";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}
@Test
public void testSummarizer_Duplicates_InRangeStart(){
    //String input = "00,3,6,7,8.0,12.0,13,00,14,15,21,22,23,24,31";
    Collection<Integer> precond = Arrays.asList(62, 62, 62, 63, 72, 73, 74);
    String result = solution.summarizeCollection(precond);
    String expect = "62-63, 72-74";
    assertEquals(expect, result, "The collect method should convert a comma-separated string into a collection of integers.");
}


}
