package api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import static baseurl.BaseUrl.spec;

public class C06DeleteBoard {
    Logger logger = LogManager.getLogger(C06DeleteBoard.class);

    @Test
    public void deleteBoard() {
        //API URL Trello
        String apiDeleteBoardUrl = "https://api.trello.com/1/boards/{id}";

        //Rest Assured ile DELETE isteği yapma
        Response response = spec
                .pathParam("id", C01PostBoard.boardId)
                .delete(apiDeleteBoardUrl);
        logger.info("Oluşturulan board silindi. Board ID: " + C01PostBoard.boardId);

        //İşlemin gerçekleştiği kontrol edilir
        Assert.assertEquals(200, response.getStatusCode());
        logger.info("Oluşturulan board un silindiği doğrulandı.");
    }
}