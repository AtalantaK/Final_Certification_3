package tests.ContractAT;

import entities.EmployeeResponse;
import helpers.UsefulMethods;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Contract AT. Получить сотрудника по ID")
public class GetEmployeeByID {
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
                //log().all().
                        when().get(endpoint + employeeId).
                then().statusCode(200);
        //log().all();

        UsefulMethods.deleteEmployee(employeeId);
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {

        int employeeId = UsefulMethods.createEmployee("Samara", "Kseniia", "Senior QA", "Kalashnikova").path("id");
        EmployeeResponse expectedEmployeeResponse = new EmployeeResponse("Samara", employeeId, "Kseniia", "Senior QA", "Kalashnikova");

        EmployeeResponse actualEmployeeResponse = given().baseUri(URI).
                //log().all().
                        when().get(endpoint + employeeId).
                then().extract().as(EmployeeResponse.class);

        assertThat(expectedEmployeeResponse).isEqualTo(actualEmployeeResponse);

        UsefulMethods.deleteEmployee(employeeId);
    }

    @Test
    @DisplayName("Найти сотрудника с несуществующим ID")
    public void getEmployeeWithNonExistenceNameTest() {

        int employeeId = 12345678;

        given().baseUri(URI).
                when().get(endpoint + employeeId).
                then().statusCode(404).
                body("error", is("Employee with id '" + employeeId + "' not found"));
    }
}
