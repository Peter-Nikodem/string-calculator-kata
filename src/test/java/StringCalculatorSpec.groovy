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

    def "adding 2 numbers returns their sum"() {
        expect:
        calculator.add('0,6') == 6
        calculator.add('5,6') == 11
        calculator.add('6,5') == 11
        calculator.add('500,0') == 500
    }

    def "adding unknown amount of numbers returns their sum"() {
        expect:
        calculator.add('0,1,3,5,7') == 16
        calculator.add('1,1,1,1,1,1,1,1') == 8
    }

    def "adding supports new lines (as well as commas) as a delimiter"() {
        expect:
        calculator.add('1\n2,3') == 6
        calculator.add('2\n5\n3\n10\n100') == 120
    }

    def "optional first line can define a new delimiter"() {
        expect:
        calculator.add(('//;\n1;2')) == 3
        calculator.add('//+\n5+6\n11,10') == 32
    }

    def "negative number is shown in the thrown exception"(){
        when:
        calculator.add('//+\n1+8,9\n-18')
        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'negatives not allowed: -18'
    }

    def "multiple negative numbers are all shown in the thrown exception"() {
        when:
        calculator.add('-1,7,-4')
        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'negatives not allowed: -1,-4'
    }

    def "numbers greater than 1000 are ignored"(){
        expect:
        calculator.add('2,1001') == 2
        calculator.add('324345,7,20,999,0,1010') == 1026
    }

    def "delimiters can be of any length"(){
        expect:
        calculator.add('//[***]\n1***2***3')==6
        calculator.add('//[%%]\n1050%%8%%10,20')==38
    }

    def "there can be multiple custom delimiters"(){
        expect:
        calculator.add("//[*][%]\n1*2%3")==6
        calculator.add("//[&][#][@][!]\n1&7#2@1!9")==20
    }

    def "multiple custom delimiters can be longer than one char"(){
        expect:
        calculator.add("//[***][%%][^%]\n1^%2***3%%0,0\n0")
    }


}
