package tests.ContractAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import helpers.Endpoints;
import helpers.UsefulMethodsDB;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Contract AT. Получить сотрудника по ID")
public class GetEmployeeByID {

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        EmployeeRequest employeeRequest = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(employeeRequest);

        given().baseUri(Endpoints.URI).
                log().all().
                when().get(Endpoints.EMPLOYEE + "/" + employeeId).
                then().statusCode(200);

        EmployeeResponse employeeResponse = new EmployeeResponse(employeeRequest.getCity(),employeeId, employeeRequest.getName(),employeeRequest.getPosition(),employeeRequest.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {

        EmployeeRequest employeeRequest = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(employeeRequest);

        EmployeeResponse expectedEmployeeResponse = new EmployeeResponse("Samara", employeeId, "Kseniia", "Senior QA", "Kalashnikova");

        EmployeeResponse actualEmployeeResponse = given().baseUri(Endpoints.URI).
                log().all().
                when().get(Endpoints.EMPLOYEE + "/" + employeeId).
                then().extract().as(EmployeeResponse.class);

        assertThat(expectedEmployeeResponse).isEqualTo(actualEmployeeResponse);

        UsefulMethodsDB.deleteEmployeeDB(actualEmployeeResponse);
    }

    @Test
    @DisplayName("Найти сотрудника с несуществующим ID")
    public void getEmployeeWithNonExistenceNameTest() {

        int employeeId = 12345678;

        given().baseUri(Endpoints.URI).
                when().get(Endpoints.EMPLOYEE + "/" + employeeId).
                then().statusCode(404).
                body("error", is("Employee with id '" + employeeId + "' not found"));
    }
}
