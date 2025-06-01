package api;

import baseurl.BaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static baseurl.BaseUrl.spec;

public class C03PostCard {

    public static List<String> cardIdList;
    Response response;
    String apiCardUrl;
    String cardName;
    JsonPath json;
    Logger logger = LogManager.getLogger(C03PostCard.class);

    @Test
    public void test03() {
        cardIdList = new ArrayList<>();

        //API URL Trello
        apiCardUrl = "https://api.trello.com/1/cards/";

        //Oluşturulacak ilk kartın adı
        cardName = "Card1";

        //Trello API İsteği
        response = spec
                .contentType("application/json")
                .queryParam("name", cardName)
                .queryParam("idList", C02PostList.listId)
                .post(apiCardUrl);
        logger.info("Card1 oluşturuldu.");


        json = response.jsonPath();
        cardIdList.add(json.getString("id"));
        logger.info("Oluşturulan card1 id si alındı. Card1 ID: " + cardIdList.get(0));

        //İşlemin gerçekleştiğini kontrol etme
        Assert.assertEquals(200, response.getStatusCode());
        logger.info("Card1 oluşturulduğu doğrulandı.");
    }

    @Test
    public void test04() {
        BaseUrl.setup();
        //Trello API Kart URL
        apiCardUrl = "https://api.trello.com/1/cards/";

        // Oluşturulacak ikinci kartın adı
        cardName = "Card2";

        // Trello API İsteği
        response = spec
                .contentType("application/json")
                .queryParam("name", cardName)
                .queryParam("idList", C02PostList.listId)
                .post(apiCardUrl);
        logger.info("Card2 oluşturuldu.");

        json = response.jsonPath();
        cardIdList.add(json.getString("id"));
        logger.info("Oluşturulan card2 id si alındı. Card2 ID: " + cardIdList.get(1));

        //İşlemin gerçekleştiği kontrol edilir
        Assert.assertEquals(200, response.getStatusCode());
        logger.info("Card2 oluşturulduğu doğrulandı.");
    }

}

