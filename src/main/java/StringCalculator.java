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
        int sum = 0;
        for (String number : separatedNumbers) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }
}
