package baseurl;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class BaseUrl {
    public static RequestSpecification spec;
    public static final String apiKey = ConfigReader.getProperty("apiKey");
    public static final String apiToken = ConfigReader.getProperty("apiToken");


    public static void setup(){
        spec = RestAssured.given()
                .contentType("application/json")
                .queryParam("key", apiKey)
                .queryParam("token", apiToken);
    }



}
