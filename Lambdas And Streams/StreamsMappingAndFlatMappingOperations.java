import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.Arrays
;

public class StreamsMappingAndFlatMappingOperations {

    public static void main(String[] args) {
        /*
          Mapping Operations :-
          These methods transform a stream’s elements into an IntStream, LongStream, 
          or DoubleStream respectively, which are specialized streams for handling 
          primitive data types efficiently.

          All these are intermediate operations
        */

        // 1. mapToInt - Transforms elements into an IntStream
        List<String> integersAsString = Arrays.asList("100", "200");
        IntStream intStream = integersAsString.stream()
                                .mapToInt(s -> Integer.parseInt(s)); // Takes an object of 'ToIntfunction<T>'. It is a functional interface containing the abstract method - 'int applyAsInt(T value)'

        System.out.println(intStream);

        // 2. mapToLong - Transforms elements to a LongStream
        List<String> longNumbersAStrings = Arrays.asList("10000000000", "20000000000");
        LongStream longStream = longNumbersAStrings.stream()
                                       .mapToLong(s -> Long.parseLong(s)); // Takes an object of 'ToLongfunction<T>'. It is a functional interface containing the abstract method - 'long applyAsLong(T value)'
        
        System.out.println(longStream);

        // 3. mapToDouble - Transforms elements to a DoubleStream
        List<String> doubleNumbersAsString = Arrays.asList("1.5", "2.5", "3.5");
        DoubleStream doubleStream = doubleNumbersAsString.stream()
                                           .mapToDouble(s -> Double.parseDouble(s)); // Takes an object of 'ToDoublefunction<T>'. It is a functional interface containing the abstract method - 'double applyAsDouble(T value)'

        System.out.println(doubleStream);

        /*
           FlatMapping Operations :-
           These operations are the "bridge" to primitives.
           These work eaxctly like flatMap, but specifically designed to result in
           primitive streams (like IntStream, LongStream etc).

           These FlatMapping Operations are all intermediate operations as well.

           We need the FlatMapping operations over the standard flatMap method because
           sometimes, we need primitive streams for performing specific operations 
           (example - sum(), max(), average() are not available in Stream but 
           available in IntStream)
        */

        // 1. flatMapToInt - Maps each element to an IntStream and flattens the result
        Stream<String> streamOfIntegralStrings = Stream.of("1,2,3", "4,5");
        IntStream flattenedIntStream = streamOfIntegralStrings
                    .flatMapToInt(
                        s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt) // Creates IntStreams of [1,2,3] and [4,5] separately and then merges into one single IntStream
                    );

        System.out.println(flattenedIntStream);

        // 2. flatMapToLong - Similar to flatMapToInt, flatMapToLong produces a LongStream
        Stream<String> streamOfLongStrings = Stream.of("10000000000,20000000000", "30000000000");
        LongStream flattenedLongStream = streamOfLongStrings
                    .flatMapToLong(
                        s -> Arrays.stream(s.split(",")).mapToLong(Long::parseLong) // Creates LongStreams of [10000000000, 20000000000] and [30000000000] separately and then merges into one single LongStream
                    );

        System.out.println(flattenedLongStream);

        // 3. flatMapToDouble - Maps each element to a DoubleStream and flattens the result
        Stream<String> streamOfDoubleStrings = Stream.of("1.1,2.2", "3.3,4.4");
        DoubleStream flattenedDoubleStream = streamOfDoubleStrings
                    .flatMapToDouble(
                        s -> Arrays.stream(s.split(",")).mapToDouble(Double::parseDouble) // Creates DoubleStreams of [1.1,2.2] and [3.3,4.4] separately and then merges into one single DoubleStream
                    );

        System.out.println(flattenedDoubleStream);

        /*
          Advanced Mapping with mapMulti :-
          Introduced in java 16, A high-performance alternative to flatMap that 
          avoids creating multiple intermediate Stream objects.

        */

        // 1. mapMulti - efficiently expands/flattens elements
        Stream.of(1, 2, 3).<String>mapMulti((number, consumer) -> {  // <String>mapMulti is used here because Java needs explicit type declaration here to know the type of content which the consumer bucket holds
            /* 
                This lambda implements 'BiConsumer<T, Consumer<R>>'.
                The abstract method is 'void accept(T element, Consumer<R> bucket)'.

                - 'number': The input element (Integer).
                - 'consumer': An object of type 'Consumer', acting as a sink/bucket 
                    to receive multiple output elements.

                Unlike flatMap, we don't return a Stream. We simply 'push' data 
                into the consumer as many times as we want.
            */
            consumer.accept(number + "a");
            consumer.accept(number + "b");
        }).forEach(s -> System.out.println(s));

        // 2. mapMulti doing flatMap's work
        List<List<Integer>> nestedList = List.of(List.of(1,2), List.of(3));
        List<Integer>flattenedVersionOfNestedList = nestedList.stream()
            .<Integer>mapMulti(
                (list, consumer) -> {

                    // Flattening happens here
                    // We iterate through the sub-list and 'push' each element to the main bucket
                    for(Integer n : list) {
                        consumer.accept(n);
                    }
                }
            ).collect(Collectors.toList());
        
        System.out.println(flattenedVersionOfNestedList);

        /*
          Boxing And Unboxing :-
          Boxing - It is the process of wrapping a primitive data (like int) into an 
          object (like Integer) so it can work with Generics and Collections.
          Example : IntStream -> boxed() -> List<Integer>

          Unboxing - It is the reverse process of extracting the raw primitive value 
          from the object for High Performance calculations. 
          Example : Stream<Integer> -> mapToInt() -> IntStream (calculations like sum(), average() etc. can be performed directly on IntStream)

          Trade Off - Boxing makes the code more flexible (as it allows using Collectors), 
                      while unboxing makes the code faster (as it removes memory overhead)
        */

        // 1. Boxing - Converts IntStream to Stream<Integer>
        IntStream primitiveIntStream = IntStream.of(10, 20, 30, 40);

        List<Integer> listOfIntegers = primitiveIntStream
                                            .boxed()  // Takes 'int' elements of IntStream one by one and wraps it into an 'Integer' object so that a Stream<Integer> is obtained. This is helpful because we can't use 'Collectors' with IntStream as 'Collectors' are designed for objects but IntStream contains raw primitives ('int' elements) which are not compatible with Java Generics.
                                            .collect(Collectors.toList());
                
        System.out.println(listOfIntegers);

        // 2. Unboxing - Converts Stream<Integer> to IntStream
        Stream<Integer> objectStream = Stream.of(100, 200, 300);
        int totalSumOfObjectStreamElements = objectStream
                        .mapToInt(n -> n.intValue()) // Each 'Integer' object is opened to extract its raw 'int' value. This converts the Stream<Integer> to IntStream
                        .sum(); // Now that we have an IntStream, we can use specialized math methods which are not available in Stream<Integer>

        System.out.println(totalSumOfObjectStreamElements);
    }   
}