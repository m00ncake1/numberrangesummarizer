Solution for: Imaan Sayed

# 1. Exec instructions
- This solution was implemented as a Maven project.
- If you do not want to install Maven, please see section 1.2
- If you have Maven installed, please see section 1.1

1.1 Execute with Maven
- to run only Solution.java: mvn exec:java
- to run tests: mvn test

1.2 Execute without Maven
- To run Solution:
java target\classes\com\imaansayed\numberrangesummarizer\Solution
- To run tests:
java target\test-classes\com\imaansayed\numberrangesummarizer\solutionTest.class

# 2. Troubleshooting
All tests pass at time of submission.

2.1 Known Problems
2.1.1 Classpath execution issue with package name
Error: "@override - method does not override or implement a method from a supertype"
Solution: 
    - comment out package name
    - cd into directory and add directory to classpath
    - javac path/to/file/Solution.java
    - java path/to/file/Solution

          
# 3. Assumptions and Design Considerations

3.1 Assumptions
- The solution should be part of the same package as the interface
- No changes must be made to the interface
- The collect method is only considered in the scope of the summarize method, so doesn't have to accomodate non-list collections as used here.

3.2 Solutions Considerations
 *   - style, incorporating Java8+
Style focusses on readability and simplicity. Where two equivalent solutions
have the same optimisation, the simpler implementation is used as a facet of 
"good code design designs for others" (i.e. readability for maintainability).
 For example, the parseIntegerList may have been implemented as a chain of Stream -> Map -> ParseInt, but requires more dependancy, and has more failure points in understanding the code, while still having an O(n) complexity like the for loop solution.

 *   - robustness
Methods are designed to have indiivudal functions for easier debugging 
and improve function design modularity.
For example, producing a counter map in summarizeCollection() is a reusable
function, so is implemented as a seperate method.

 *   - best practices
 By breaking up the logic into multiple methods, each with fewer decision points, the cyclomatic complexity of the main method is reduced to improve testability.

 See above in robustness. Project is implemented as a standard Maven project. Maven was learnt
 specifically for the sake of this project, as build tools like Maven were described as best practice for portable, standard java project set up, execution and builds when research was conducted.

 Project uses "java standards" wherever possible, e.g. JUnit tests, docstrings for documentation, and Maven for builds/execution.
 Unit tests cover the functionality of "primary" methods, testing (currently) single-use 
 utility methods indirectly therein.
 Unit tests cover both normal and edge cases
