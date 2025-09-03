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

@DisplayName("Contract AT. Получить сотрудника по имени")
public class GetEmployeeByName {

    private static String URI = "https://innopolispython.onrender.com";
    private static String endpoint = "/employee/name/";

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        String employeeName = "Kseniia";
        int employeeId = UsefulMethods.createEmployee("Samara", employeeName, "Senior QA", "Kalashnikova").path("id");


        given().baseUri(URI).
                //log().all().
                        when().get(endpoint + employeeName).
                then().statusCode(200);
        //log().all();

        UsefulMethods.deleteEmployee(employeeId);
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {

        String employeeName = "Kseniia";
        int employeeId = UsefulMethods.createEmployee("Samara", employeeName, "Senior QA", "Kalashnikova").path("id");

        EmployeeResponse employeeResponse = given().baseUri(URI).
                //log().all().
                        when().get(endpoint + employeeName).
                then().extract().as(EmployeeResponse.class);

        assertThat(employeeResponse.getName()).isEqualTo(employeeName);

        UsefulMethods.deleteEmployee(employeeId);
    }

    @Test
    @DisplayName("Найти сотрудника с несуществующим именем")
    public void getEmployeeWithNonExistenceNameTest() {

        String employeeName = "TestKseniiaForAT";

        given().baseUri(URI).
                when().get(endpoint + employeeName).
                then().statusCode(404).
                body("error", is("Employee with name '" + employeeName + "' not found"));
    }
}
