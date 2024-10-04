import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventarioChromeFunctionalTest extends BaseFunctionalTest {

    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver = initializeBrowser(Browser.CHROME);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DEFAULT_DURATION_IN_SECONDS));
    }

    @Test
    @Order(1)
    void whenAddNewProductFromChromeThenOK() {
        super.testAddNewProductOK();
    }

    @Test
    @Order(2)
    void whenEditNewProductFromChromeThenOK() {
        super.testEditProductOK();
    }

    @Test
    @Order(3)
    void whenDeleteNewProductFromChromeThenOK() {
        super.testDeleteProductOK();
    }
}