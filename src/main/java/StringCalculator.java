/**
 * @author Peter Nikodem
 */
public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;
        String[] split = numbers.split(",");
        if (split.length==1){
            return Integer.parseInt(split[0]);
        } else {
            return Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
        }

    }
}
