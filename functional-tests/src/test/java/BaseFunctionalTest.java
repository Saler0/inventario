import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseFunctionalTest {

    private static final String CHROME_BROWSER_NAME = "chrome";
    private static final String FIREFOX_BROWSER_NAME = "firefox";
    private static final String NOT_IMPLEMENTED_EXCEPTION_MSG = "Not implemented yet";
    private static final String NEW_PRODUCT_NAME = "Oster coffee maker";
    private static final String NEW_PRODUCT_DESCRIPTION = "Cafetera de filtro";
    private static final String NEW_PRODUCT_STOCK = "15";
    private static final String UPDATED_PRODUCT_NAME = "DeLonghi coffee maker";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "Cafetera espresso";
    private static final String UPDATED_PRODUCT_STOCK = "10";
    private static final String NEW_PRODUCT_CREATED_OK_MSG = "Producto creado exitosamente";
    private static final String PRODUCT_EDITED_OK_MSG = "Producto editado exitosamente";
    private static final String PRODUCT_DELETED_OK_MSG = "Producto borrado exitosamente";
    private static final String BUTTON_OPEN_MODAL_ID = "btn-open-modal";
    private static final String MODAL_PRODUCTS_ID = "modalProducts";
    private static final String FIELD_NOMBRE_ID = "nombre";
    private static final String FIELD_DESCRIPCION_ID = "descripcion";
    private static final String FIELD_STOCK_ID = "stock";
    private static final String BUTTON_SAVE_MODAL_ID = "btn-save-modal";
    private static final String CONFIRMATION_MESSAGE_XPATH = "/html/body/div[2]";
    private static final String CONFIRMATION_MESSAGE_TITLE_ID = "swal2-title";
    private static final String BUTTON_MODAL_OK_XPATH = "/html/body/div[2]/div/div[6]/button[1]";
    private static final String PRODUCTS_TABLE_LAST_ROW_XPATH = "//*[@id=\"root\"]/div/div[1]/div[2]/div/div/table/tbody/tr[last()]/td[position()>1]";
    private static final String BUTTON_EDIT_PRODUCT_XPATH = "//*[@id=\"root\"]/div/div[1]/div[2]/div/div/table/tbody/tr[last()]/td[last()]/button[1]";
    private static final String BUTTON_DELETE_PRODUCT_XPATH = "//*[@id=\"root\"]/div/div[1]/div[2]/div/div/table/tbody/tr[last()]/td[last()]/button[2]";
    private static final String MODAL_DELETE_PRODUCT_XPATH = "/html/body/div[2]/div";
    private static final String BUTTON_CONFIRM_DELETE_XPATH = "/html/body/div[2]/div/div[6]/button[1]";
    private static final String PROPERTIES_FILE_PATH = "src/test/resources/application.properties";
    private static final String SELENIUM_SERVER_PROPERTY_KEY = "selenium.server.url";
    private static final String INVENTARIO_FRONTEND_PROPERTY_KEY = "inventario.frontend.url";
    private static final String DEFAULT_SELENIUM_SERVER_URL = "http://localhost:4444";
    private static final String DEFAULT_INVENTARIO_FRONTEND_URL = "http://localhost:3000";

    protected static final int WAIT_DEFAULT_DURATION_IN_SECONDS = 40;
    protected static final int WAITING_TIME_AFTER_SUBMIT_IN_SECONDS = 5;

    private final Properties properties = new Properties();
    private final String seleniumServerUrl = getProperty(SELENIUM_SERVER_PROPERTY_KEY, DEFAULT_SELENIUM_SERVER_URL);
    private final String inventarioFrontendUrl = getProperty(INVENTARIO_FRONTEND_PROPERTY_KEY, DEFAULT_INVENTARIO_FRONTEND_URL);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public WebDriver initializeBrowser(Browser browser) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (browser) {
            case CHROME:
                capabilities.setBrowserName(CHROME_BROWSER_NAME);
                break;
            case FIREFOX:
                capabilities.setBrowserName(FIREFOX_BROWSER_NAME);
                break;
            default:
                throw new RuntimeException(NOT_IMPLEMENTED_EXCEPTION_MSG);
        }

        return new RemoteWebDriver(new URL(seleniumServerUrl), capabilities);
    }

    protected void testAddNewProductOK() {
        driver.get(inventarioFrontendUrl);

        WebElement addProductButton = driver.findElement(By.id(BUTTON_OPEN_MODAL_ID));
        addProductButton.click();

        WebElement modal = driver.findElement(By.id(MODAL_PRODUCTS_ID));
        wait.until(ExpectedConditions.visibilityOf(modal));

        WebElement productNameField = driver.findElement(By.id(FIELD_NOMBRE_ID));
        productNameField.sendKeys(NEW_PRODUCT_NAME);

        WebElement descriptionField = driver.findElement(By.id(FIELD_DESCRIPCION_ID));
        descriptionField.sendKeys(NEW_PRODUCT_DESCRIPTION);

        WebElement stockField = driver.findElement(By.id(FIELD_STOCK_ID));
        stockField.sendKeys(NEW_PRODUCT_STOCK);

        WebElement saveButton = driver.findElement(By.id(BUTTON_SAVE_MODAL_ID));
        saveButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAITING_TIME_AFTER_SUBMIT_IN_SECONDS));

        WebElement confirmationMessage = driver.findElement(By.xpath(CONFIRMATION_MESSAGE_XPATH));
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));

        WebElement modalMessage = driver.findElement(By.id(CONFIRMATION_MESSAGE_TITLE_ID));
        wait.until(ExpectedConditions.visibilityOf(modalMessage));

        assertThat(modalMessage.getText()).isEqualTo(NEW_PRODUCT_CREATED_OK_MSG);

        WebElement modalOkButton = driver.findElement(By.xpath(BUTTON_MODAL_OK_XPATH));
        wait.until(ExpectedConditions.visibilityOf(modalOkButton));

        modalOkButton.click();

        List<String> productsTableLastRowItems = driver.findElements(By.xpath(PRODUCTS_TABLE_LAST_ROW_XPATH)).stream()
                .map(WebElement::getText)
                .toList();

        assertThat(productsTableLastRowItems).containsAll(List.of(NEW_PRODUCT_NAME, NEW_PRODUCT_DESCRIPTION, NEW_PRODUCT_STOCK));
    }

    void testEditProductOK() {
        driver.get(inventarioFrontendUrl);

        WebElement editProductButton = driver.findElement(By.xpath(BUTTON_EDIT_PRODUCT_XPATH));
        editProductButton.click();

        WebElement modal = driver.findElement(By.id(MODAL_PRODUCTS_ID));
        wait.until(ExpectedConditions.visibilityOf(modal));

        WebElement productNameField = driver.findElement(By.id(FIELD_NOMBRE_ID));
        productNameField.clear();
        productNameField.sendKeys(UPDATED_PRODUCT_NAME);

        WebElement descriptionField = driver.findElement(By.id(FIELD_DESCRIPCION_ID));
        descriptionField.clear();
        descriptionField.sendKeys(UPDATED_PRODUCT_DESCRIPTION);

        WebElement stockField = driver.findElement(By.id(FIELD_STOCK_ID));
        stockField.clear();
        stockField.sendKeys(UPDATED_PRODUCT_STOCK);

        WebElement saveButton = driver.findElement(By.id(BUTTON_SAVE_MODAL_ID));
        saveButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAITING_TIME_AFTER_SUBMIT_IN_SECONDS));

        WebElement confirmationMessage = driver.findElement(By.xpath(CONFIRMATION_MESSAGE_XPATH));
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));

        WebElement modalMessage = driver.findElement(By.id(CONFIRMATION_MESSAGE_TITLE_ID));
        wait.until(ExpectedConditions.visibilityOf(modalMessage));

        assertThat(modalMessage.getText()).isEqualTo(PRODUCT_EDITED_OK_MSG);

        WebElement modalOkButton = driver.findElement(By.xpath(BUTTON_MODAL_OK_XPATH));
        wait.until(ExpectedConditions.visibilityOf(modalOkButton));

        modalOkButton.click();

        List<String> productsTableLastRowItems = driver.findElements(By.xpath(PRODUCTS_TABLE_LAST_ROW_XPATH)).stream()
                .map(WebElement::getText)
                .toList();

        assertThat(productsTableLastRowItems).doesNotContainAnyElementsOf(List.of(NEW_PRODUCT_NAME, NEW_PRODUCT_DESCRIPTION, NEW_PRODUCT_STOCK));
        assertThat(productsTableLastRowItems).containsAll(List.of(UPDATED_PRODUCT_NAME, UPDATED_PRODUCT_DESCRIPTION, UPDATED_PRODUCT_STOCK));
    }

    void testDeleteProductOK() {
        driver.get(inventarioFrontendUrl);

        WebElement deleteProductButton = driver.findElement(By.xpath(BUTTON_DELETE_PRODUCT_XPATH));
        deleteProductButton.click();

        WebElement modal = driver.findElement(By.xpath(MODAL_DELETE_PRODUCT_XPATH));
        wait.until(ExpectedConditions.visibilityOf(modal));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAITING_TIME_AFTER_SUBMIT_IN_SECONDS));

        WebElement confirmDeleteButton = driver.findElement(By.xpath(BUTTON_CONFIRM_DELETE_XPATH));
        confirmDeleteButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAITING_TIME_AFTER_SUBMIT_IN_SECONDS));

        WebElement confirmationMessage = driver.findElement(By.xpath(CONFIRMATION_MESSAGE_XPATH));

        try {
            wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
        } catch(StaleElementReferenceException e) {
            confirmationMessage = driver.findElement(By.xpath(CONFIRMATION_MESSAGE_XPATH));
        }

        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));

        WebElement modalMessage = driver.findElement(By.id(CONFIRMATION_MESSAGE_TITLE_ID));
        wait.until(ExpectedConditions.visibilityOf(modalMessage));

        assertThat(modalMessage.getText()).isEqualTo(PRODUCT_DELETED_OK_MSG);

        WebElement modalOkButton = driver.findElement(By.xpath(BUTTON_MODAL_OK_XPATH));
        wait.until(ExpectedConditions.visibilityOf(modalOkButton));

        modalOkButton.click();

        List<String> productsTableLastRowItems = driver.findElements(By.xpath(PRODUCTS_TABLE_LAST_ROW_XPATH)).stream()
                .map(WebElement::getText)
                .toList();

        assertThat(productsTableLastRowItems).doesNotContainAnyElementsOf(List.of(NEW_PRODUCT_NAME, NEW_PRODUCT_DESCRIPTION, NEW_PRODUCT_STOCK));
        assertThat(productsTableLastRowItems).doesNotContainAnyElementsOf(List.of(UPDATED_PRODUCT_NAME, UPDATED_PRODUCT_DESCRIPTION, UPDATED_PRODUCT_STOCK));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    private String getProperty(String propertyKey, String defaultValue) {
        try (FileInputStream stream = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(stream);
            return properties.getProperty(propertyKey, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
