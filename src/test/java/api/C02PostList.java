package api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import static baseurl.BaseUrl.spec;

public class C02PostList {

    public static String listId;
    Logger logger = LogManager.getLogger(C02PostList.class);
    @Test
    public void test02() {

        //API URL Trello
        String apiListUrl = "https://api.trello.com/1/lists/";

        //Oluşturulacak listenin adı
        String listName = "List1";

        //Trello API İsteği
        Response response = spec
                .contentType("application/json")
                .queryParam("name", listName)
                .queryParam("idBoard", C01PostBoard.boardId)
                .post(apiListUrl);
        logger.info("List oluşturuldu.");

        JsonPath json = response.jsonPath();
        listId = json.getString("id");
        logger.info("Oluşturulan list id si alındı. List ID: "+ listId);

        //İşlemin gerçekleştiği kontrol edilir
        Assert.assertEquals(200,response.getStatusCode());
        logger.info("List oluşturulduğu doğrulandı.");

    }

}
