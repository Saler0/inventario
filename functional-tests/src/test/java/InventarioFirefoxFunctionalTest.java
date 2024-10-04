import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventarioFirefoxFunctionalTest extends BaseFunctionalTest {

    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver = initializeBrowser(Browser.FIREFOX);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DEFAULT_DURATION_IN_SECONDS));
    }

    @Test
    @Order(1)
    void whenAddNewProductFromFirefoxThenOK() {
        super.testAddNewProductOK();
    }

    @Test
    @Order(2)
    void whenEditNewProductFromFirefoxThenOK() {
        super.testEditProductOK();
    }

    @Test
    @Order(3)
    void whenDeleteNewProductFromFirefoxThenOK() {
        super.testDeleteProductOK();
    }
}