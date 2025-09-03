package tests.ContractAT;

import entities.EmployeeResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("Contract AT. Получение списка всех сотрудников")
public class GetEmployees {

    //given() — что отправляем, when() — куда отправляем, then() — что ожидаем

    private static String URI = "https://innopolispython.onrender.com";
    private static String endpoint = "/employees";

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        given().baseUri(URI).
                log().all().
                when().get(endpoint).
                then().statusCode(200).
                log().all();
    }

    @Test
    @DisplayName("Проверить тело ответа")
    //todo: дописать проверку - а, ну мы можем получить данные из БД и сравнить с данными из АПИ!
    public void checkResponseBodyTest() {

        EmployeeResponse[] employees = given().baseUri(URI).
//                log().all().
                when().get(endpoint).
                then().
//                log().all().
                extract().as(EmployeeResponse[].class);

        for (EmployeeResponse employee : employees) {
            System.out.println(employee);
        }
    }

//    public static void main(String[] args) {
//        Employee employee1 = new Employee(100, "Xenia", "QA", "K.");
//        Employee employee2 = new Employee("Moscow", 200, "Anton", "DEV", "B.");
//        System.out.println(employee1);
//        System.out.println(employee2);
//    }
}
