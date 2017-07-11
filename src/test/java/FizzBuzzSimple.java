import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FizzBuzzSimple {

    private static final int DIVISIBLE_BY_THREE = 1;
    private static final int DIVISIBLE_BY_FIVE = 2;
    private static final int DIVISIBLE_BY_FIFTEEN = 3;

    public static void main(String[] args) {
        FizzBuzzSimple fizzBuzz = new FizzBuzzSimple();
        fizzBuzz.printTransformedNumbersBetween(20, 20, System.out);
        System.out.println();
    }

    public void printTransformedNumbersBetween(int start, int end, PrintStream printStream) {
        validateInput(start, end);

        OutputStream o = printStream;

        String transformedNumberLabel;
        Map<String, Integer> summaryMap = initializeSummaryMap();

        for(int oneNumber = start; oneNumber <= end ;oneNumber++){
            transformedNumberLabel = transformNumberToFizzBuzz(oneNumber);
            printStream.print(transformedNumberLabel);
            count(summaryMap, transformedNumberLabel);
        }


        summaryMap.forEach((key, value) -> printStream.print(" " + key +" " + value));

    }

    private void count(Map<String, Integer> summaryMap, String transformedNumberLabel){
        String summaryKey = generateSummaryKey(transformedNumberLabel);
        Integer currentValue = summaryMap.get(summaryKey);
        currentValue = currentValue + 1;
        summaryMap.put(summaryKey, currentValue);
    }

    private Map<String, Integer> initializeSummaryMap() {
        Map<String, Integer> summaryMap = new LinkedHashMap<>();
        summaryMap.put("fizz", 0);
        summaryMap.put("buzz", 0);
        summaryMap.put("fizzbuzz", 0);
        summaryMap.put("integer", 0);
        return summaryMap;
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
            throw new IllegalArgumentException("end value must be greater then 0, provided value is "+ end);
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

}
