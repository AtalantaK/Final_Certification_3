package helpers;

import entities.EmployeeRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tests.ContractAT.UpdateEmployee;

import static io.restassured.RestAssured.given;

public class UsefulMethods {

    private static String URI = "https://innopolispython.onrender.com";

    public static Response createEmployee(String city, String name, String position, String surname) {

        String endpoint = "/employee";

        RestAssured.useRelaxedHTTPSValidation();

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).name(name).position(position).surname(surname).build();
        String token = Authorization.getToken();

        System.out.println("Создаю сотрудника...");

        Response response = given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint);

        System.out.println("Создан сотрудник с id = " + response.path("id"));

        return response;
    }

    public static Response deleteEmployee(int employeeId) {

        String endpoint = "/employee/";

        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Удаляю сотрудника с id = " + employeeId);

        Response response = given().baseUri(URI).
                log().all().
                when().delete(endpoint + employeeId);

        System.out.println("Удалён сотрудник с id = " + employeeId);

        return response;

    }

    public static Response getEmployeeByName(String employeeName) {

        String endpoint = "/employee/name/";

        return given().baseUri(URI).
                log().all().
                when().get(endpoint + employeeName);
    }

    public static Response getEmployeeByID(int employeeId) {

        String endpoint = "/employee/";

        System.out.println("Ищу сотрудника с id = " + employeeId);

        return given().baseUri(URI).
                log().all().
                when().get(endpoint + employeeId);
    }

    public static Response updateEmployeeCompletely(int id, String city, String name, String position, String surname) {

        String endpoint = "/employee/";

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).name(name).position(position).surname(surname).build();
        String token = Authorization.getToken();

        return given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(endpoint + id);
    }

    public static Response updateEmployeeCityPosition(int id, String city, String position) {

        String endpoint = "/employee/";

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).position(position).build();
        String token = Authorization.getToken();

        return given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(endpoint + id);
    }

    public static Response getEmployees() {
        String endpoint = "/employees";

        return given().baseUri(URI).
                log().all().
                when().get(endpoint);
    }
}
