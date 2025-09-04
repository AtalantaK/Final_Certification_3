package helpers;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UsefulMethods {
    public static Response createEmployee(String city, String name, String position, String surname) {
        String URI = "https://innopolispython.onrender.com";
        String endpoint = "/employee";

        RestAssured.useRelaxedHTTPSValidation();

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).name(name).position(position).surname(surname).build();
        String token = Authorization.getToken();

        System.out.println("Создаю сотрудника...");

        return given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint);
    }

    public static Response deleteEmployee(int employeeId) {
        String URI = "https://innopolispython.onrender.com";
        String endpoint = "/employee/";

        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Удаляю сотрудника с id = " + employeeId);

        return given().baseUri(URI).
                log().all().
                when().delete(endpoint + employeeId);

    }

    public static Response getEmployeeByName(String employeeName) {
        String URI = "https://innopolispython.onrender.com";
        String endpoint = "/employee/name/";

        return given().baseUri(URI).
                log().all().
                when().get(endpoint + employeeName);
    }

    public static Response getEmployeeByID(int employeeId) {

        String URI = "https://innopolispython.onrender.com";
        String endpoint = "/employee/";

        System.out.println("Ищу сотрудника с id = " + employeeId);

        return given().baseUri(URI).
                log().all().
                when().get(endpoint + employeeId);
    }
}
