package utilities;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public abstract class TestBase {

    protected WebDriver driver;
    protected Random random;

    @Before
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }


    @After
    public void tearDown() throws Exception {
        wait(2);
        driver.quit();
    }


    //HARD WAIT
    public void wait(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Select VisibleText DDM
    public void selectVisibleText(WebElement ddm) {
        Select select = new Select(ddm);
        try {
            select.selectByVisibleText("2 adet");
            Assert.assertTrue(select.getFirstSelectedOption().getText().contains("2"));
        } catch (Exception e) {
            select.selectByVisibleText("1 adet");
        }

    }

    //SearchBox Find Element Dynamic Locate
    public void randomClickElementHandle(List<WebElement> list){
        random = new Random();
        int randomIdx = random.nextInt(list.size());
        boolean result = true;
        do {
            try {
                list.get(randomIdx).click();
                result = false;
            } catch (Exception e) {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            }
        } while (result);
    }

    //Excelde dosya yolunu dinamik almak için
    public  String getPath(String excelFileName) {
        String path = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + excelFileName;
        return path;
    }


}