package tests.ContractAT;

import helpers.Endpoints;
import helpers.UsefulMethodsAPI;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayName("Contract AT. Удалить сотрудника по айди")
public class DeleteEmployee {

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        int employeeId = UsefulMethodsAPI.createEmployeeAPI("Samara", "Kseniia", "Senior QA", "Kalashnikova").path("id");

        given().baseUri(Endpoints.URI).
                log().all().
                when().delete(Endpoints.EMPLOYEE + "/" + employeeId).
                then().statusCode(200).
                log().all();
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {

        int employeeId = UsefulMethodsAPI.createEmployeeAPI("Samara", "Kseniia", "Senior QA", "Kalashnikova").path("id");

        given().baseUri(Endpoints.URI).
                log().all().
                when().delete(Endpoints.EMPLOYEE + "/" + employeeId).
                then().body("message", is("Deleted")).
                log().all();
    }

    @Test
    @DisplayName("Удалить несуществующего сотрудника")
    @Disabled("Есть актуальный баг")
    public void deleteNonExistentEmployeeTest() {

        int employeeId = 12345;

        given().baseUri(Endpoints.URI).
                log().all().
                when().delete(Endpoints.EMPLOYEE + "/" + employeeId).
                then().statusCode(404).
                body("message", is("Employee with employee_id = " + employeeId + " not found")).
                log().all();
    }
}
