package pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by programmer on 12/18/16.
 */
public class ProfilePage extends FluentPage {

    @FindBy(name = "addTaste")
    FluentWebElement addTasteButton;
    @FindBy(name = "save")
    FluentWebElement saveButton;

    @Override
    public String getUrl() {
        return "/profile";
    }

    @Override
    public void isAt() {
        assertThat(findFirst("h2").getText()).isEqualTo("Your Profile");
    }

    public void fillInfos(String twitterHandle, String email, String birthDate) {
        fill("#twitterHandle").with(twitterHandle);
        fill("#email").with(email);
        fill("#birthDate").with(birthDate);
    }

    public void addTaste(String taste) {
        addTasteButton.click();
        fill("#tastes0").with("spring");
    }

    public void saveProfile() {
        saveButton.click();
    }
}
