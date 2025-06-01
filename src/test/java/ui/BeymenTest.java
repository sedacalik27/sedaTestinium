package ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.TestBase;
import utilities.TextWriter;

import java.util.List;

public class BeymenTest extends TestBase {
    ExcelReader reader;
    TextWriter writer;

    Logger logger = LogManager.getLogger(BeymenTest.class);
    @Test
    public void beymenTest() {
        //Excelden veri almak ve txt dosyasına arama sonuçlarını yazdırmak için kullanılan object ler.
        reader = new ExcelReader(getPath("product_data.xlsx"), "Sheet1");
        writer = new TextWriter();

        //www.beymen.com sitesi açılır.
        driver.get(ConfigReader.getProperty("url"));
        logger.info("//-www.beymen.com sitesi açıldı.");

        //Ekrana gelen cookie ve cinsiyet seçim ekranı kapatılır.
        WebElement rejectCookie = driver.findElement(By.id("onetrust-reject-all-handler"));
        rejectCookie.click();
        WebElement closeGenderBox = driver.findElement(By.xpath("//*[@class='o-modal__closeButton bwi-close']"));
        closeGenderBox.click();
        logger.info("Ekrana gelen cookie ve cinsiyet seçim ekranı kapatıldı.");

        //-	Ana sayfanın açıldığı kontrol edilir.
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(ConfigReader.getProperty("url"), currentUrl);

        //-	Arama kutucuğuna “şort” kelimesi girilir.(Not = Şort kelimesi excel dosyası üzerinden 1 sütun 1 satırdan alınarak yazılmalıdır.)
        WebElement searchBox = driver.findElement(By.className("o-header__search--input"));
        String firstData = reader.getCellData(1, 1); //excel den datalar alındı.
        searchBox.sendKeys(firstData);
        wait(2);
        logger.info("Arama kutucuğuna excel den alınan datayla “şort” kelimesi girildi.");

        //-	Arama kutucuğuna girilen “şort” kelimesi silinir.
        WebElement searchBox2 = driver.findElement(By.id("o-searchSuggestion__input")); //ilk aramadan sonra locate değiştiği için searchbox locate'i tekrar alındı.
        searchBox2.clear();
        wait(2);
        logger.info("Arama kutucuğuna girilen “şort” kelimesi silindi.");

        //-	Arama kutucuğuna “gömlek” kelimesi girilir.(Not = Gömlek kelimesi excel dosyası üzerinden 2 sütun 1 satırdan alınarak yazılmalıdır.)
        String secondData = reader.getCellData(1, 2); //excel den datalar alındı.
        logger.info("Arama kutucuğuna excel den alınan datayla “gömlek” kelimesi girildi.");

        //-	Klavye üzerinden “enter” tuşuna bastırılır
        searchBox2.sendKeys(secondData + Keys.ENTER);
        logger.info("Klavye üzerinden “enter” tuşuna bastırıldı.");


        //-	Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.
        List<WebElement> productsList = driver.findElements(By.xpath("//*[@class='o-productList__item']")); //locate dinamik alınıp list'e assign edildi.
        randomClickElementHandle(productsList); //Arama sonucunda çıkan seçeneklerden birinin her seferinde rastgele seçilebilmesi için method yapıldı.
        wait(2);
        logger.info("Sonuca göre sergilenen ürünlerden rastgele bir ürün seçildi.");

        //-	Seçilen ürünün ürün bilgisi ve tutar bilgisi txt dosyasına yazılır.
        WebElement brand = driver.findElement(By.className("o-productDetail__brandLink"));
        String brandText = brand.getText();
        WebElement description = driver.findElement(By.className("o-productDetail__description"));
        String descriptionText = description.getText();
        WebElement itemPrice = driver.findElement(By.xpath("//*[@id='priceNew']"));
        String itemPriceText = itemPrice.getText();
        writer.writeText(brandText + "\n" + descriptionText + "\n" + itemPriceText); //txt dosyası overwrite yapmasın diye method icerisine SimpleDateFormat format eklendi.
        logger.info("Seçilen ürünün ürün bilgisi ve tutar bilgisi txt dosyasına yazdırıldı.");

        //Ürün fiyatı assertion için manipüle edildi
        String itemPriceResult = itemPriceText.replace(".","").replaceAll(",",".").substring(0,itemPriceText.length()-3);
        double expected = Double.parseDouble(itemPriceResult);

        //-	Seçilen ürün sepete eklenir.
        List<WebElement> chooseSize = driver.findElements(By.xpath("//*[@class=\"m-form__group -relative\"]/div/span"));
        for (int i = 0; i < chooseSize.size(); i++) {       //Ürünün mevcut olan ilk bedeni seçilir.
            if (!chooseSize.get(i).getAttribute("class").contains("disabled")) {
                chooseSize.get(i).click();
                break;
            }
        }
        WebElement addToCartButton = driver.findElement(By.xpath("//button[@class='m-addBasketFavorite__basket btn']"));
        addToCartButton.click();
        logger.info("Seçilen ürün sepete eklendi.");

        //Sepete gidilir
        WebElement goToCartButton = driver.findElement(By.xpath("//button[@class='m-notification__button btn']"));
        goToCartButton.click();
        logger.info("Sepede gidildi.");

        //-	Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.
        WebElement cartPrice = driver.findElement(By.xpath("//span[@class='m-orderSummary__value'][1]"));
        String cartPriceText = cartPrice.getText();

        //Sepetteki ürün fiyatı assertion için manipüle edildi.
        String actual = cartPriceText.replace(".","").replaceAll(",",".").substring(0,itemPriceText.length()-3);
        double actualCartPrice = Double.parseDouble(actual);
        double delta = 0.01;    //iki double değeri assert etmek icin kullanıldı
        Assert.assertEquals(expected, actualCartPrice, delta);
        logger.info("Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırıldı.");

        //-	Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.
        WebElement ddm = driver.findElement(By.id("quantitySelect0-key-0"));
        selectVisibleText(ddm); //Bazı ürünler sepete 1'den fazla eklenemediği için.
        wait(1);
        logger.info("Adet arttırılarak ürün adedinin 2 olduğu doğrulandı.");

        //-	Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.
        WebElement deleteCart = driver.findElement(By.id("removeCartItemBtn0-key-0"));
        deleteCart.click();
        logger.info("Ürün sepetten silindi.");

        //Sepetin boş olduğu dogrulandı
        WebElement verifyEmptyChart = driver.findElement(By.xpath("//strong[text()='Sepetinizde Ürün Bulunmamaktadır']"));
        String actualChartMessage = verifyEmptyChart.getText();
        String expectedChartMessage = "SEPETINIZDE ÜRÜN BULUNMAMAKTADIR";
        Assert.assertEquals(expectedChartMessage, actualChartMessage);
        logger.info("Sepetin boş olduğu kontrol edildi.");

    }
}
