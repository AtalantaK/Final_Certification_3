package helpers;

import entities.EmployeeRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsefulMethods {

    public static Response createEmployee(String city, String name, String position, String surname) {

        RestAssured.useRelaxedHTTPSValidation();

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).name(name).position(position).surname(surname).build();
        String token = Authorization.getToken();

        System.out.println("Создаю сотрудника...");

        Response response = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(Endpoints.EMPLOYEE);

        System.out.println("Создан сотрудник с id = " + response.path("id"));

        return response;
    }

    public static Response deleteEmployee(int employeeId) {

        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Удаляю сотрудника с id = " + employeeId + "...");

        Response response = given().baseUri(Endpoints.URI).
                log().all().
                when().delete(Endpoints.EMPLOYEE + employeeId);

        System.out.println("Удалён сотрудник с id = " + employeeId);

        return response;

    }

    public static Response getEmployeeByName(String employeeName) {

        System.out.println("Ищу сотрудника с именем = " + employeeName + "...");

        return given().baseUri(Endpoints.URI).
                log().all().
                when().get(Endpoints.EMPLOYEE + Endpoints.NAME + employeeName);
    }

    public static Response getEmployeeByID(int employeeId) {

        System.out.println("Ищу сотрудника с id = " + employeeId + "...");

        return given().baseUri(Endpoints.URI).
                log().all().
                when().get(Endpoints.EMPLOYEE + employeeId);
    }

    public static Response updateEmployeeCompletely(int employeeId, String city, String name, String position, String surname) {

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).name(name).position(position).surname(surname).build();
        String token = Authorization.getToken();

        System.out.println("Полностью обновляю сотрудника с id = " + employeeId + "...");

        return given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + employeeId);
    }

    public static Response updateEmployeeCityPosition(int employeeId, String city, String position) {

        System.out.println("Частично обновляю сотрудника с id = " + employeeId + "...");

        EmployeeRequest requestJSON = EmployeeRequest.builder().city(city).position(position).build();
        String token = Authorization.getToken();

        return given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + employeeId);
    }

    public static Response getEmployees() {

        System.out.println("Получаю список всех сотрудников...");

        return given().baseUri(Endpoints.URI).
                log().all().
                when().get(Endpoints.EMPLOYEES);
    }
}
