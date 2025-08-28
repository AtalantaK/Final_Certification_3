package tests.ContractAT;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@DisplayName("Контрактные кейсы для получения списка всех сотрудников")
public class getEmployees {

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
    //todo: дописать проверку или удалить этот кейс
    public void checkResponseBodyTest() {

        Employee[] employees = given().baseUri(URI).
//                log().all().
                when().get(endpoint).
                then().
//                log().all().
                extract().as(Employee[].class);

        for (Employee employee : employees) {
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
