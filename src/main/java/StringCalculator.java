/**
 * @author Peter Nikodem
 */
public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        int sum = 0;
        String normalizedNumbers = numbers.replace('\n', ',');
        String[] separatedNumbers = normalizedNumbers.split(",");
        for (String number : separatedNumbers) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }
}
