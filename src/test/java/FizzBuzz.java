import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FizzBuzz {

    private static final int DIVISIBLE_BY_THREE = 1;
    private static final int DIVISIBLE_BY_FIVE = 2;
    private static final int DIVISIBLE_BY_FIFTEEN = 3;

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.printTransformedNumbersBetween(1, 20, System.out);
        System.out.println();
    }

    public void printTransformedNumbersBetween(int start, int end, PrintStream printStream) {
        validateInput(start, end);

        Map<String, Integer> summary = IntStream.rangeClosed(start, end)
                .mapToObj((oneNumber) -> transformNumberToFizzBuzz(oneNumber))
                .map((oneNumber) -> { printStream.print(oneNumber +" "); return oneNumber;})
                .collect(Collectors.groupingBy((key) -> generateSummaryKey(key),
                        Collectors.summingInt(s -> 1)));

        summary.forEach((key, value) -> System.out.print(" " + key +" " + value));

    }
    private String generateSummaryKey(String key){
        if(key.contains("fizz") || key.contains("buzz")){
            return key;
        }
        return "integer";
    }

    private void validateInput(int start, int end){
        //I made some assumptions about validations to keep solution simple
        if(start <= 0){
            throw new IllegalArgumentException("start value must be greater then 0, provided value is "+ start);
        }
        if(end <= 0){
            throw new IllegalArgumentException("start value must be greater then 0, provided value is "+ end);
        }

        if(start > end){
            throw new IllegalArgumentException("start value must be less then end value, provided values are start=" + start +", end = "+ end);
        }

    }

    private String transformNumberToFizzBuzz(int number){
        boolean isDividableByThree = number % 3 == 0? true:false;
        boolean isDividableByFive = number % 5 == 0? true:false;
        boolean isDividableByFifteen = isDividableByThree && isDividableByFive;

        if(isDividableByFifteen){
            return "fizzbuzz";
        }

        if(isDividableByThree){
            return "fizz";
        }

        if(isDividableByFive){
            return "buzz";
        }

        return String.valueOf(number);

    }

    private String transformNumberA(int number){
        int outcome = 0;
        outcome = number % 3 == 0? outcome | 0x0001 : outcome;
        outcome = number % 5 == 0? outcome | 0x0002 : outcome;

        switch (outcome){
            case DIVISIBLE_BY_FIFTEEN :
                return "FizzBuzz";
            case DIVISIBLE_BY_THREE :
                return "Fizz";
            case DIVISIBLE_BY_FIVE :
                return "Buzz";
            default:
                return String.valueOf(number);
        }



    }

}
