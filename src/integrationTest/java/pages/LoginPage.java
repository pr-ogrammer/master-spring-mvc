package pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by programmer on 12/18/16.
 */
public class LoginPage extends FluentPage {

    @FindBy(name = "twitterSignin")
    FluentWebElement signinButton;

    @Override
    public String getUrl() {
        return "/login";
    }

    @Override
    public void isAt() {
        assertThat(findFirst("h2").getText()).isEqualTo("Login page");
    }

    public void login() {
        signinButton.click();
    }
}
