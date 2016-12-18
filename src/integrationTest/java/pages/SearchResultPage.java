package pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by programmer on 12/18/16.
 */
public class SearchResultPage extends FluentPage {

    @FindBy(css = "ul.collection")
    FluentWebElement resultList;

    public void isAt(String... keywords) {
        assertThat(findFirst("h2").getText()).isEqualTo("Search results: " + String.join(",", keywords));
    }

    public int getNumberOfResults() {
        return resultList.find("li").size();
    }
}
