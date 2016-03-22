import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author Peter Nikodem
 */
public class StringCalculator {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String NEW_LINE_DELIMITER = "\n";

    public int add(String numbersString) {
        if (numbersString.isEmpty()) {
            return 0;
        }
        return add(getAllNumbers(numbersString));
    }

    private int add(Stream<Integer> numbers){
        return numbers.collect(summingInt(Integer::intValue));
    }

    private Stream<Integer> getAllNumbers(String numbers) {
        numbers = handleOptinalDelimiterDefiningLine(numbers);
        numbers = replaceDelimiterWithTheDefault(NEW_LINE_DELIMITER,
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
            String customDelimiter = getDelimiter(numbers);
            numbers = removeDelimiterDefiningLine(numbers);
            return replaceDelimiterWithTheDefault(customDelimiter, numbers);
        }
        return numbers;
    }

    private boolean isDelimiterDefiningLinePresent(String numbers) {
        return numbers.startsWith("//");
    }

    private String getDelimiter(String numbers) {
        String delimiter = numbers.substring(2,numbers.indexOf('\n'));
        if (delimiter.length()==1) {
            return delimiter;
        }
        return delimiter.substring(1, delimiter.length() - 1);
    }

    private String replaceDelimiterWithTheDefault(String delimiter, String numbers) {
        return numbers.replace(delimiter, DEFAULT_DELIMITER);
    }

    private String removeDelimiterDefiningLine(String numbers) {
        return numbers.substring(numbers.indexOf('\n')+1);
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

    private boolean isNotOverThousand(Integer integer) {
        return integer<=1000;
    }

    private boolean isNegative(Integer integer) {
        return integer<0;
    }
}
