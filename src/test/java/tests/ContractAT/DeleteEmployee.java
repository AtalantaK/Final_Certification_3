package tests.ContractAT;

import entities.EmployeeRequest;
import helpers.Authorization;
import helpers.UsefulMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayName("Contract AT. Удалить сотрудника по айди")
public class DeleteEmployee {

    private static String URI = "https://innopolispython.onrender.com";
    private static String endpoint = "/employee/";

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        int employeeId = UsefulMethods.createEmployee("Samara", "Kseniia", "Senior QA", "Kalashnikova").path("id");

        given().baseUri(URI).
                log().all().
                when().delete(endpoint + employeeId).
                then().statusCode(200).
                log().all();
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {

        int employeeId = UsefulMethods.createEmployee("Samara", "Kseniia", "Senior QA", "Kalashnikova").path("id");

        given().baseUri(URI).
                log().all().
                when().delete(endpoint + employeeId).
                then().body("message", is("Deleted")).
                log().all();
    }

    @Test
    @DisplayName("Удалить несуществующего сотрудника")
    @Disabled("Есть актуальный баг")
    public void deleteNonExistentEmployeeTest() {

        int employeeId = 12345;

        given().baseUri(URI).
                log().all().
                when().delete(endpoint + employeeId).
                then().statusCode(404).
                body("message", is("Employee with employee_id = " + employeeId + " not found")).
                log().all();
    }
}
