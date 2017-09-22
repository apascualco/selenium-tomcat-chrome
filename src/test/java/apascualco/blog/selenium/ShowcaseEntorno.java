package apascualco.blog.selenium;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assume.assumeTrue;

public class ShowcaseEntorno {

    protected static Tomcat tomcat;

    @BeforeClass
    public static void levantarTomcatShowcase() throws FileNotFoundException, LifecycleException {
        TomcatEmbedded.levantarShowcase();
        tomcat = TomcatEmbedded.server;
    }

    protected void configureChrome(String path) {
        assumeTrue(new File(path).exists());
        System.setProperty("webdriver.chrome.driver", path);
    }

}
