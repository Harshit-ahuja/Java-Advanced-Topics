import java.util.List;
import java.util.stream.Stream;

public class StreamsIntro {
    public static void main(String[] args) {
        List<String> fruits = List.of("Apple", "Banana", "Kiwi", "Orange", "Mango");

        Stream<String> streamOfFruits = fruits.stream();

        /*
         'forEach' method takes an argument of 'Consumer' type which is a 
         functional interface and has just one abstract method 'accept' of void type 
         which takes one generic type argument.

         So, we create a consumer object using lambda and pass that object to 'forEach' method
           Consumer obj = (fruit) -> System.out.println(fruit);
        */ 
        streamOfFruits.forEach((fruit) -> System.out.println(fruit));

        // This is how streams heavily make use of lambda expressions

        /*
          One more important point about streams is that
          Once, a stream is processed, you can't process it again.
          For example - the 'streamOfFruits' stream won't be processed again.
          It would give - 'IllegalStateException: stream has already been operated upon or closed'
          if you try to process it again.
        */ 
        streamOfFruits.forEach((fruit) -> System.out.println(fruit));
    }
}
