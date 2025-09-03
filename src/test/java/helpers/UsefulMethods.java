package helpers;

import entities.EmployeeRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsefulMethods {
    public static Response createEmployee() {
        String URI = "https://innopolispython.onrender.com";
        String endpoint = "/employee";

        RestAssured.useRelaxedHTTPSValidation();

        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Ivan").position("QA").surname("Ivanov").build();
        String token = Authorization.getToken();

        System.out.println("Создаю сотрудника...");

        return given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                when().post(endpoint);
    }

    public static Response deleteEmployee(int employee_id) {
        String URI = "https://innopolispython.onrender.com";
        String endpoint = "/employee/";

        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Удаляю сотрудника с id = " + employee_id);

        return given().baseUri(URI).
                when().delete(endpoint + employee_id);

    }
}
