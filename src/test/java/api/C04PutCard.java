package api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static baseurl.BaseUrl.spec;

public class C04PutCard {

    Random random = new Random();
    Logger logger = LogManager.getLogger(C04PutCard.class);

    @Test
    public void updateCardName() {
        //API URL Trello 
        int randomIdx = random.nextInt(C03PostCard.cardIdList.size());
        String apiUpdateCardUrl = "https://api.trello.com/1/cards/" + C03PostCard.cardIdList.get(randomIdx);

        //Güncellenecek kartın yeni adı
        String newCardName = "UpdatedCard1";

        //Trello API İsteği
        Response updateResponse = spec
                .contentType("application/json")
                .queryParam("name", newCardName)
                .put(apiUpdateCardUrl);
        logger.info("Rastgele seçilen card ın ismi değiştirildi. Card ID : "+ C03PostCard.cardIdList.get(randomIdx));


        //İşlemin gerçekleştiği kontrol edilir
        Assert.assertEquals(200,updateResponse.getStatusCode());
        logger.info("Rastgele bir card ismi değiştirildiği doğrulandı.");
    }
}