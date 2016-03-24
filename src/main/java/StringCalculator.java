import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author Peter Nikodem
 */
public class StringCalculator {

    protected Logger logger = LogManager.getLogger(StringCalculator.class);
    private static final String DEFAULT_DELIMITER = ",";

    public int add(String numbersString) {
        if (numbersString.isEmpty()) {
            return 0;
        }
        return add(getAllEligibleNumbers(numbersString));
    }

    private int add(Stream<Integer> numbers) {
        return logAndReturn(
                numbers.collect(summingInt(Integer::intValue)));
    }

    private int logAndReturn(int sum) {
        logger.info(sum);
        return sum;
    }

    private Stream<Integer> getAllEligibleNumbers(String numbers) {
        numbers = handleOptinalDelimiterDefiningLine(numbers);
        numbers = replaceDelimiterWithTheDefault("\n",
                numbers);
        String[] separatedNumbers = numbers.split(DEFAULT_DELIMITER);
        Supplier<Stream<Integer>> allNumbers = () -> Arrays.asList(separatedNumbers).
                stream().
                map(Integer::parseInt).
                filter(this::isNotOverThousand);
        List<Integer> negativeNumbers = allNumbers.get().
                filter(this::isNegative).
                collect(toList());
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException(prepareNegativeNumbersErrorMessage(negativeNumbers));
        }
        return allNumbers.get();
    }

    private String handleOptinalDelimiterDefiningLine(String numbers) {
        if (isDelimiterDefiningLinePresent(numbers)) {
            String customDelimitersDefinition = getDelimiterDefinitions(numbers);
            for (String customDelimiter : retrieveDelimiters(customDelimitersDefinition)) {
                numbers = replaceDelimiterWithTheDefault(customDelimiter, numbers);
            }
            numbers = removeDelimiterDefiningLine(numbers);
            return numbers;
        }
        return numbers;
    }

    private List<String> retrieveDelimiters(String definition) {
        List<String> delimiters = new ArrayList<>();
        while (definition.startsWith("[")) {
            delimiters.add(definition.substring(1, definition.indexOf("]")));
            definition = definition.substring(definition.indexOf("]") + 1);
        }
        if (!definition.isEmpty()) {
            delimiters.add(definition);
        }

        return delimiters;
    }

    private boolean isDelimiterDefiningLinePresent(String numbers) {
        return numbers.startsWith("//");
    }

    private String getDelimiterDefinitions(String numbers) {
        return numbers.substring(2, numbers.indexOf('\n'));
    }

    private String replaceDelimiterWithTheDefault(String delimiter, String numbers) {
        return numbers.replace(delimiter, DEFAULT_DELIMITER);
    }

    private String removeDelimiterDefiningLine(String numbers) {
        return numbers.substring(numbers.indexOf('\n') + 1);
    }

    private String prepareNegativeNumbersErrorMessage(List<Integer> negativeNumbers) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("negatives not allowed: ");
        errorMessage.append(negativeNumbers.get(0));
        for (Integer i : negativeNumbers.subList(1, negativeNumbers.size())) {
            errorMessage.append(DEFAULT_DELIMITER);
            errorMessage.append(i);
        }
        return errorMessage.toString();
    }

    private boolean isNotOverThousand(Integer integer) {
        return integer <= 1000;
    }

    private boolean isNegative(Integer integer) {
        return integer < 0;
    }
}
