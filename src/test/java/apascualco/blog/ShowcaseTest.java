package apascualco.blog;

import apascualco.blog.selenium.ShowcaseEntorno;
import org.apache.catalina.LifecycleState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

public class ShowcaseTest extends ShowcaseEntorno {

    private WebDriver driver;

    @Before
    public void testUp() {

        assumeFalse(tomcat.getHost() == null);
        assumeTrue(tomcat.getServer().getState() == LifecycleState.STARTED);

        if(this.driver == null) {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setPlatform(Platform.ANY);
            this.configureChrome("/Users/alberto/Desktop/Desarrollo/herramientas/chromedriver");
            this.driver = new ChromeDriver(capabilities);
        }
    }

    @After
    public void testDown() {
        this.driver.close();
    }

    @Test
    public void test() {
        this.driver.get("http://" + tomcat.getServer().getAddress() + ":8080/showcase-5.3/");
        WebElement elementLink = this.driver.findElement(By.id("SubMenu-Input"));
        elementLink.click();
        String autocompletePath = "//a[contains(@class, 'SubMenuLink') and text() = '• AutoComplete']";
        elementLink = this.driver.findElement(By.xpath(autocompletePath));
        elementLink.click();
        Assert.assertEquals("http://localhost:8080/showcase-5.3/ui/input/autoComplete.xhtml", this.driver.getCurrentUrl());
        String editorPath = "//a[contains(@class, 'SubMenuLink') and text() = '• Editor']";
        elementLink = this.driver.findElement(By.xpath(editorPath));
        elementLink.click();
        Assert.assertEquals("http://localhost:8080/showcase-5.3/ui/input/editor.xhtml", this.driver.getCurrentUrl());
    }
}
