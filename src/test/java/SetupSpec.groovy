import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class SetupSpec extends Specification {

    def "spock and groovy dependencies were correctly imported"(){
        when: int x = 3
        then: x*2 == 6
    }
}
