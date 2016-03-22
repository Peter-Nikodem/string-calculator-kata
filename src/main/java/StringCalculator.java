import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author Peter Nikodem
 */
public class StringCalculator {
    private static final char DEFAULT_DELIMITER = ',';
    private static final String DEFAULT_DELIMITER_STRING = ",";
    private static final char NEW_LINE_DELIMITER = '\n';

    public int add(String numbersString) {
        if (numbersString.isEmpty()) {
            return 0;
        }
        return getAllNumbers(numbersString)
                .collect(summingInt(Integer::intValue));
    }

    private Stream<Integer> getAllNumbers(String numbers) {
        numbers = handleOptinalDelimiterDefiningLine(numbers);
        numbers = replaceDelimiterWithTheDefault(NEW_LINE_DELIMITER,
                numbers);
        String[] separatedNumbers = numbers.split(DEFAULT_DELIMITER_STRING);
        Stream<Integer> allNumbers = Arrays.asList(separatedNumbers).
                stream().
                map(Integer::parseInt);
        List<Integer> negativeNumbers = allNumbers.
                filter(this::isNegative).
                collect(toList());
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException(prepareNegativeNumbersErrorMessage(negativeNumbers));
        }
        return allNumbers;
    }

    private String handleOptinalDelimiterDefiningLine(String numbers) {
        if (isDelimiterDefiningLinePresent(numbers)) {
            char customDelimiter = getDelimiter(numbers);
            numbers = removeDelimiterDefiningLine(numbers);
            return replaceDelimiterWithTheDefault(customDelimiter, numbers);
        }
        return numbers;
    }

    private boolean isDelimiterDefiningLinePresent(String numbers) {
        return numbers.startsWith("//");
    }

    private char getDelimiter(String numbers) {
        return numbers.charAt(2);
    }

    private String replaceDelimiterWithTheDefault(char delimiter, String numbers) {
        return numbers.replace(delimiter, DEFAULT_DELIMITER);
    }

    private String removeDelimiterDefiningLine(String numbers) {
        return numbers.substring(4);
    }

    private String prepareNegativeNumbersErrorMessage(List<Integer> negativeNumbers) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("negatives not allowed: ");
        errorMessage.append(negativeNumbers.get(0));
        for (Integer i : negativeNumbers.subList(1, negativeNumbers.size())) {
            errorMessage.append(',');
            errorMessage.append(i);
        }
        return errorMessage.toString();
    }

    private boolean isNegative(Integer integer) {
        return integer<0;
    }
}
