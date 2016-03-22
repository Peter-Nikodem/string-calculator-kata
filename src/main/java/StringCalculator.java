/**
 * @author Peter Nikodem
 */
public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;
        String[] separatedNumbers = numbers.split(",");
        if (separatedNumbers.length==1){
            return parseToInt(separatedNumbers[0]);
        } else {
            int sum=0;
            for (String number:separatedNumbers) {
                sum += parseToInt(number);
            }
            return sum;
        }

    }

    private int parseToInt(String s) {
        return Integer.parseInt(s);
    }
}
