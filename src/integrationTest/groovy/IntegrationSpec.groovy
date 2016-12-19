import geb.Configuration
import geb.pages.LoginPage
import geb.pages.ProfilePage
import geb.pages.SearchResultPage
import geb.spock.GebSpec
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication

/**
 * Created by programmer on 12/19/16.
 */
@SpringBootTest(
        classes = MasterSpringMvcApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration
class IntegrationSpec extends GebSpec {

    @Value('${local.server.port}')
    int port

    Configuration createConf() {
        def configuration = super.createConf()
        configuration.baseUrl = "http://localhost:$port"
        configuration
    }

    def "should redirect to login page"() {
        when: "open main page"
        go '/'
//        report("test-geb")
        then: "should redirect to login page"
        $('h2', 0).text() == 'Login page'
    }

    def "Should redirect to profile page after login"() {
        when:
        to LoginPage
        loginWithTwitter()
        and:
        go '/'
        then:
        $('h2').text() == 'Your Profile'
    }

    def "Po zdefiniowaniu profilu wyświetlane są wyniki odpowiadające jego preferencjom"() {
        given:
        to LoginPage
        loginWithTwitter()
        and:
        to ProfilePage
        when:
        fillInfos("programmer", "programmer@email.com", "03/19/1985");
        addTaste("spring")
        and:
        saveProfile()
        then:
        at SearchResultPage
        page.results.size() == 2
}
}
