package api;

import baseurl.BaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import static baseurl.BaseUrl.spec;

public class C01PostBoard {

    public static String boardId;
    Logger logger = LogManager.getLogger(C01PostBoard.class);

    @Test
    public void test01() {
        BaseUrl.setup();

        //API URL Trello
        String apiUrl = "https://api.trello.com/1/boards/";

        //Oluşturulacak board un adı
        String boardName = "Board1";

        //Trello API isteği
        Response response = spec
                .contentType("application/json")
                .queryParam("name", boardName)
                .post(apiUrl);
        logger.info("Board oluşturuldu.");

        JsonPath json = response.jsonPath();
        boardId = json.getString("id");
        logger.info("Oluşturulan board id si alındı. Board ID: " + boardId);

        //İşlemin gerçekleştiği kontrol edilir
        Assert.assertEquals(200, response.getStatusCode());
        logger.info("Board oluşturulduğu doğrulandı.");

    }

}
