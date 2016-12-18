import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pages.LoginPage;
import pages.ProfilePage;
import pages.SearchResultPage;
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication;
import pl.prutkowski.master.spring.mvc.auth.StubSocialSigninConfig;
import pl.prutkowski.master.spring.mvc.controller.search.StubTweeterSearchConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by programmer on 12/17/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {
                MasterSpringMvcApplication.class,
                StubTweeterSearchConfig.class,
                StubSocialSigninConfig.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class FluentIntegrationTest extends FluentTest {

    @Page
    private LoginPage loginPage;
    @Page
    private ProfilePage profilePage;
    @Page
    private SearchResultPage searchResultPage;

    @Value("${local.server.port}")
    private int serverPort;

    @Override
    public WebDriver getDefaultDriver() {
        return new PhantomJSDriver();
    }

    @Override
    public String getDefaultBaseUrl() {
        return "http://localhost:" + serverPort;
    }

    @Test
    public void hasPageTitle() {
        goTo("/");
        assertThat(findFirst("h2").getText()).isEqualTo("Login page");
    }

    @Test
    public void shouldBeRedirectedAfterFillingForm() {
        goTo("/");
        loginPage.isAt();
        loginPage.login();
        profilePage.isAt();
        profilePage.fillInfos("programmer", "programmer@email.com", "03/19/1985");
        profilePage.addTaste("spring");
        profilePage.saveProfile();
////        takeScreenShot();
        searchResultPage.isAt("spring");
        assertThat(searchResultPage.getNumberOfResults()).isEqualTo(2);
    }
}
