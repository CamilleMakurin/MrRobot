package v2.map.generator;


import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;
import v2.configuration.Configuration;
import v2.core.log.Log;
import v2.map.citydata.CityData;
import v2.map.citydata.CityDataRepository;

import java.util.List;

/**
 * Application that reads city data from csv file, launches website with map that runs on localhost
 * and generates map images based on city data
 */
@Component
public class MapGenerator {

    private final String PROP_WEBDRIVER = "webdriver.gecko.driver";

    private CityDataRepository cityDataRepository;

    public MapGenerator(CityDataRepository cityDataRepository) {
        this.cityDataRepository = cityDataRepository;
    }

    public void generateMaps() {
        List<CityData> cityData = cityDataRepository.getCityData();
        cityData.forEach(this::downloadMap);
    }

    private void downloadMap(CityData d) {
        try {
            WebDriver browser = getWebDriver();
            browser.get(Configuration.MAP_URL);
            Thread.sleep(5000);
            fillInputFields(browser, "widthInput", "35");
            fillInputFields(browser, "heightInput", "35");
            fillInputFields(browser, "dpiInput", "300");
            fillInputFields(browser, "latInput", "12");
            fillInputFields(browser, "lonInput", "12");
            fillInputFields(browser, "zoomInput", "13.5");
            fillInputFields(browser, "latInput", d.getLatitude());
            fillInputFields(browser, "lonInput", d.getLongitude());

            WebElement zoomInput = browser.findElement(By.id("zoomInput"));
            zoomInput.sendKeys(Keys.ENTER);
            browser.findElement(By.id("heightInput")).click();

            Thread.sleep(15000);
            browser.findElement(By.id("generate-btn")).click();
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Log.error("Failed to Thread.sleep: " + e.getMessage());
        }
    }

    private WebDriver getWebDriver() {
        System.setProperty(PROP_WEBDRIVER, Configuration.GEKO_DRIVER_PATH);

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", Configuration.RAW_MAP_DIRECTORY);
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.download.viewableInternally.enabledTypes", "");
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "image/png");

        FirefoxDriver driver = new FirefoxDriver(options);
        Dimension screenDimension = new Dimension(2000, 1000);
        driver.manage().window().setSize(screenDimension);
        return driver;
    }

    private void fillInputFields(WebDriver browser, String inputId, String input) throws InterruptedException {
        WebElement widthInput = browser.findElement(By.id(inputId));
        widthInput.clear();
        widthInput.sendKeys(input);
        Thread.sleep(2000);
    }
}
