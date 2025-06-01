package api;


import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import static baseurl.BaseUrl.spec;

public class C05DeleteCard {
    Logger logger = LogManager.getLogger(C05DeleteCard.class);

    @Test
    public void deleteCard() {
        //API URL Trello
        String apiDeleteCardUrl = "https://api.trello.com/1/cards/{id}";

        for (int i = 0; i < C03PostCard.cardIdList.size(); i++) {
            //Rest Assured ile istek yapma
            Response response = spec
                    .pathParam("id", C03PostCard.cardIdList.get(i))
                    .delete(apiDeleteCardUrl);
            logger.info("Oluşturulan card lar silindi. Cards ID: "+ C03PostCard.cardIdList.get(i));

            //İşlemin gerçekleştiği kontrol edilir
            Assert.assertEquals(200,response.getStatusCode());
            logger.info("Oluşturalan card ların silindiği doğrulandı.");
        }

    }
}
