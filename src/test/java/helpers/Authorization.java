package helpers;

import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Authorization {

    private static String URI = "https://innopolispython.onrender.com";
    private static String endpoint = "/login";
    private static String username = "admin";
    private static String password = "admin";

    public static String getToken() {

        RestAssured.useRelaxedHTTPSValidation();
        User requestJSON = new User(username, password);

        String token = given().
                baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                //log().all().
                when().post(endpoint).path("token");

        //System.out.println(token);

        return token;
    }

    public static void main(String[] args) {
        Authorization.getToken();
    }
}
