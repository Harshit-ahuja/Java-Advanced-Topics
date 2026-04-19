import java.util.*;
import java.util.stream.Collectors;

public class StreamOperations {
    public static void main(String[] args) {
        List<Integer> integerList = List.of(1,5,8,9,14,3,25,9,5,3,14);

        // Find if any number exist in the list which is greater than 20
        Boolean anyNumberGreaterThan20 = integerList.stream()
            .anyMatch(n -> n > 20); // anyMatch takes object of 'Predicate' type. It is a functional interface having abstract method - 'test' which returns boolean value. Similar to anyMatch method, we have allMatch method as well which checks if the predicate is true for all the elements in the stream. allMatch would return false if there even exist a single element in the stream that does not satisfy the predicate condition.

        System.out.println(anyNumberGreaterThan20);

        // Remove duplicates from list
        List<Integer> distinctList = integerList.stream()
            .distinct()
            .collect(Collectors.toList());

        System.out.println(distinctList);

        // Counting the number of elements in List
        Long sizeOfList = integerList.stream()
            .count();

        System.out.println(sizeOfList);

        // Sorting the list
        List<Integer> sortedList = integerList.stream()
            .sorted()
            .collect(Collectors.toList());

        // Drop numbers as long as they are < 10
        List<Integer> dropWhileResult = sortedList.stream()
            .dropWhile(n -> n < 10) // Introduced in Java 9. Takes a 'Predicate' type object. Drops elements from the beginning of a stream as long as given condition is true. As soon as the condition becomes false, it stops dropping and includes that element and all subsequent elements in the result, regardless of the condition.
            .collect(Collectors.toList());

        System.out.println(dropWhileResult); 

        // Transforming Data
        List<Integer> squaredList = integerList.stream()
            .map(number -> number*number) // 'Map' is an intermediate operation for transforming data. Lambda expression inside map() provides an implementation of the 'Function<T, R>' functional interface which contains the single abstract method - 'R apply(T t)'. Hence, map() requires the abstract method 'apply' to take an input object of type <T> and return another object <R> for given input.
            .collect(Collectors.toList());
        
        System.out.println(squaredList);

        // Aggregation/Accumulation of Data
        Integer sum = integerList.stream()
            .reduce(0, (a,b) -> a+b); 
            
            /*
                'Reduce' is a terminal operation. It combines all elements into one single result (Sum, Product, Min, Max).
                It takes 2 values as arguments - The Identity value and an object of type BinaryOperator<T> (that we supply through lambda).

                Identity value - It is the initial seed value for the accumulation process and acts as the default result if the stream is empty.
                                 In the first iteration, the accumulator takes the identity value, so it must be a neutral element (example - 0 for sum, 1 for product), so it does not change the result of the first element.
                BinaryOperator<T> - It is a functional interface having the single abstract method 'T apply (T a, T b)'
                                    First argument(a) of the apply method is called the accumulator. It acts as the variable that carries state of reduction. It stores the partial result from all previous steps (starting with Identity value).
                                    Second argument(b) of apply method represents the next item being pulled from the stream to be merged into the accumulator.

                                    The 'apply(T a, T b)' method executes the lambda logic to combine these two inputs into a single output (in our case, sum) which then becomes the new accumulator for the next element processing of stream.
            */ 

        System.out.println(sum);


        // Concatenation of Strings using 'Reduce'
        List<String> brokenStrings = List.of("Java", "is", "cool");
        String completeSentence = brokenStrings.stream().reduce("", (a,b) -> a.isEmpty() ? b : a + " " + b);
        System.out.println(completeSentence);


        // Filtering only perfect squares
        List<Integer> perfectSquares = integerList.stream()
            .filter(n -> {                    // Intermediate operation of streams, used when we need to filter the entire stream based on a specific condition. Takes an object of type 'Predicate' (a functional interface that an abstract method which returns a boolean value)
                double sqrt = Math.sqrt(n);
                return sqrt % 1 == 0;         // '9' is a perfect square, it's sqrt would be '3'. 3 when divided by 1 would leave remainder 0. On the other hand, 8 isn't a perfect square. It's sqrt would be 2.82 & when divided by 1, it would leave the remainder 0.82 (not equal to 0). Hence, if sqrt of a number doesn't give integral value, it isn't a perfect square
            })
            .collect(Collectors.toList());
        
        System.out.println(perfectSquares);
        
        // Combining multiple lists into single lists
        List<String> battersList = List.of("Rohit", "Virat", "Shreyas");
        List<String> bowlersList = List.of("Bumrah", "Shami", "Arshdeep");

        List<List<String>> players = List.of(battersList, bowlersList);

        List<String> flatListOfPlayers = players.stream()
            .flatMap(list -> list.stream()) 
            .collect(Collectors.toList());
        
            /*
              'flatMap' is used when you have a Container inside a Container 
              (like a List of Lists) 
              and you want to unpack everything into a single flat container.
              In short, 'flatMap' is used when the goal is to remove nesting.

              Working of flatmap :
              -> 'players' object was a List<List<String>>
              -> 'players.stream()' became a Stream<List<String>>
              -> 'flatMap' has 2 phases - the 'map' phase and the 'flattening' phase
              -> Map Phase :
                    flatMap picks up each element(list) one by one and applies the
                    lambda function to it.
                    This lambda function converts that list into a new mini-stream.

                    Lambda inside the 'flatMap' method provides an implementation of 
                    'Function<T, Stream<R>>' functional interface which contains the single abstract method - 'Stream<R> apply(T t)'.
                    flatMap wants this apply method to take any object <T> but always return a stream. 
                    (In our case, we provided it the sub-lists and returned streams of those lists)
                    
              -> Flatten Phase :
                    At this brief internal moment, Java technically holds a Stream of Streams.
                    flatMap then opens all these mini-streams and pours all their elements into
                    one single Stream.
                    This results in a simple linear stream of elements. 
            */

        System.out.println(flatListOfPlayers);
    }
}
