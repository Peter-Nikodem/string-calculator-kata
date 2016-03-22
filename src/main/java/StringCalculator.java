import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Peter Nikodem
 */
public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        if (numbers.startsWith("//")){
            char newDelimiter = numbers.charAt(2);
            numbers = numbers.substring(4);
            numbers = numbers.replace(newDelimiter, ',');
        }
        String normalizedNumbers = numbers.replace('\n', ',');
        String[] separatedNumbers = normalizedNumbers.split(",");
        List<Integer> allNumbers = new ArrayList<>();
        int sum = 0;
        for (String number : separatedNumbers) {
            allNumbers.add(Integer.parseInt(number));
        }
        List<Integer> negativeNumbers = allNumbers.stream().filter((i -> i<0)).collect(Collectors.toList());
        if (!negativeNumbers.isEmpty()){
            throw new IllegalArgumentException(prepareErrorMessage(negativeNumbers));
        }
        for (Integer number :allNumbers) {
            sum += number;
        }
        return sum;
    }

    private String prepareErrorMessage(List<Integer> negativeNumbers) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("negatives not allowed: ");
        errorMessage.append(negativeNumbers.get(0));
        for (Integer i : negativeNumbers.subList(1,negativeNumbers.size())){
            errorMessage.append(',');
            errorMessage.append(i);
        }
        return errorMessage.toString();
    }
}
