package com.imaansayed.numberrangesummarizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Solution for NmberRangeSummarizer problem of impact.com assessment
 * @author Imaan Sayed
 */
public class Solution implements NumberRangeSummarizer {
    
/**
* Accepts String[] list of integers and returns ArrayList<Integer> 
* Throws IllegalArgumentException for any non-integer string in list, e.g. 5.0.
* If primary method receives bad delimeter, will be treated be as a bad list item and expect an int here.
* Therefore, bad input also caught here treated as possibly also bad delimeter in String input.
* @param  input  A string list of integers
* @return      ArrayList<Integer>
*/
private List<Integer> parseIntegerList(String[] stringArr){
    ArrayList<Integer> intList = new ArrayList<Integer>();

    try {
        for(String num : stringArr) intList.add(Integer.valueOf(
                                                num.strip()));
      }
      catch(NumberFormatException e) {
        throw new IllegalArgumentException("Expected String[] integer list, got something else.\n Possibly bad delimeter or non-int number.", e);
        
      }
    return intList;
}
    
/**
* Accepts a string representing a list of integers, e.g. "1,20,4,5,9" and
* returns it parsed as an ArrayList<Integer>
* @param  input  A string representing a list of integers
* @return      ArrayList<Integer>
*/
@Override
public  Collection<Integer> collect(String input){
  if (input == null || input.isEmpty()) {
    return List.of();
  }
  // convert string -> string[]
  String[] stringNums = input.split(",");

  return parseIntegerList(stringNums);
};

  /**
* Accepts a Collection of Integers and
* returns an ordered string list of numbers and sequential ranges,
* e.g. "1, 5, 9-12, 14"
* @param input A sorted or unsorted Collection<Integer> e.g. ArrayList<Integer>
* @return      Sorted string list of integers, and ranges where integers are sequential
*/
@Override
public String summarizeCollection(Collection<Integer> input) {
    
  // Create hashmap of each integer and its frequency
  // Used to handle repeated numbers and to create set of input elements
    Map<Integer, Long> numberCounts = getCounterMap(input);

    // Create sorted arraylist with ONLY unique elements
    // 0 and 1 length lists have O(1) sorting cost, so premature sorting not a liability
    List<Integer> sortedList = new ArrayList<>(numberCounts.keySet());
    Collections.sort(sortedList);

    if (sortedList.isEmpty()) return "Empty input";
    
    // If sortedList has ONE item, that only occurs once...
    if (sortedList.size() == 1 
        && numberCounts.get(sortedList.get(0)) == 1) {
        return sortedList.get(0).toString();
    }

    StringJoiner result = new StringJoiner(", ");

    // range pointers
    int rStart = 0;
    int rEnd;

    // Generate ranges and individual numbers
    for (int i = 0; i < sortedList.size(); ) {
      rStart = i;
      rEnd = findRangeEnd(sortedList, rStart);
      addRangeToResult(sortedList, rStart, rEnd, numberCounts, result);
      i = rEnd + 1;
      }
    return result.toString();
}

private int findRangeEnd(List<Integer> sortedList, int rangeStart) {
  int rangeEnd = rangeStart;

  // While not at the end of the list, keep checking if not item in order
  // ...is sequential
  while (rangeEnd + 1 < sortedList.size() 
        && sortedList.get(rangeEnd + 1) - sortedList.get(rangeEnd) == 1) {
      rangeEnd++;
  }
  return rangeEnd;
}

/**
 * Adds a range or individual numbers to the result.
 * 
 */
private void addRangeToResult(List<Integer> sortedList, int rangeStart, int rangeEnd, Map<Integer, Long> numberCounts, StringJoiner result) {
  int startIntVal = sortedList.get(rangeStart);
  int endIntVal = sortedList.get(rangeEnd);

  // If adjacent numbers form a range
  if (rangeEnd > rangeStart) {
      result.add(startIntVal + "-" + endIntVal);
  } else {
      // If not part of a range, add the number individually
      result.add(String.valueOf(startIntVal));

      // If there are duplicates, add each individually
      for (int i = 1; i < numberCounts.get(endIntVal); i++) {
          result.add(String.valueOf(endIntVal));
      }
  }
}

/** Creates a TreeMap of each item in input collection (key) 
 * and its frequency therein (value) */
private Map<Integer,Long> getCounterMap(Collection<Integer> input){
  return input.stream()
            .collect(
              Collectors.groupingBy(
                num -> num, TreeMap::new, Collectors.counting()));
    
}
public  static void main(String[] args){
  Solution sol = new Solution();
  System.out.println( sol.summarizeCollection(sol.collect("40,41,42,42,42")));
}

}
