import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class StringCalculatorSpec extends Specification {
    StringCalculator calculator = new StringCalculator();

    def "adding empty string returns 0"() {
        expect:
        calculator.add('') == 0
    }

    def "adding 1 number returns the number"() {
        expect:
        calculator.add('0') == 0
        calculator.add('9') == 9
        calculator.add('657') == 657
    }

    def "adding 2 numbers returns their sum"(){
        expect:
        calculator.add('0,6') == 6
        calculator.add('5,6') == 11
        calculator.add('6,5') == 11
        calculator.add('500,0') == 500
    }


}
