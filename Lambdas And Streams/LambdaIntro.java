// Interface
interface Walkable{
    int walk (int steps, boolean isWalking);
}

// Implementation Class
class WalkFast implements Walkable {

    @Override
    public int walk(int steps, boolean isWalking) {
        System.out.println("Walking Fast " + steps + " steps");
        return 2 * steps;
    }
}

public class LambdaIntro {
    public static void main(String[] args) {

        // Initializing a Walkable object with the help of WalkFast class
        Walkable obj = new WalkFast();
        obj.walk(4, true);

        /* 
          Initializing a Walkable object with the help of an 'anonymous class' :-

            Walkable obj2 = new Walkable() {
                @Override
                public int walk(int steps) {
                    System.out.println("Not Walking");
                    return 0;
                }
            };

        */ 


        /*
          Above 2 ways are there for creating object of Walkable type.
          But since, 'Walkable' interface is a functional interface (i.e it has only one abstract method),
          we have another way to create an object of 'Walkable' type and that is 'lambda' expression

          The implementation that we provide (lambdaObj, in our case) is automatically
          considered the implementation of the only abstract method that is present 
          inside the functional interface.
        */ 
        Walkable lambdaObj = (steps, isWalking) -> {
            System.out.println("Walking " + steps + " steps using lambdaObj");
            return steps;
        };
        lambdaObj.walk(5, true);

        /*
          The paramater names do not need to be absolutely same either as defined 
          in the abstract method of the interface.
          It will be auto-matched by type.
          (Now we have used a different parameter name - 'mySteps')
          
          Also, if you just want to write a single line in the abstract method 
          implementation (in our case - just a return statement), we can skip the 
          curly braces as well and simply return the value which the walk method needs to.
          
          Also, we can skip the 'return' keyword too.

          Also, if our method had only one argument, we could skip the braces - '()'
          around arguments as well.
        */
        Walkable lambdaObj2 = (mySteps, isWalking) -> 3*mySteps;

        System.out.println(lambdaObj2.walk(9, true));
    }
}