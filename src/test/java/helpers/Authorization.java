package helpers;

import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class Authorization {

    private static String username = "admin";
    private static String password = "admin";

    public static String getToken() {

        RestAssured.useRelaxedHTTPSValidation();
        User requestJSON = new User(username, password);

        return given().
                baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                log().all().
                when().post(Endpoints.AUTH).jsonPath().getString("token");
    }

    public static void main(String[] args) {
        Authorization.getToken();
    }
}
