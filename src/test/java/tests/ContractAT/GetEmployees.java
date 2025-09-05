package tests.ContractAT;

import helpers.Endpoints;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("Contract AT. Получение списка всех сотрудников")
public class GetEmployees {

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        given().baseUri(Endpoints.URI).
                log().all().
                when().get(Endpoints.EMPLOYEES).
                then().statusCode(200).
                log().all();
    }
}
