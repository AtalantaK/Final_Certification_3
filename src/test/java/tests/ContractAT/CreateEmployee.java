package tests.ContractAT;

import entities.EmployeeRequest;
import entities.ErrorResponse;
import helpers.Authorization;
import helpers.UsefulMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Contract AT. Создание нового сотрудника")
public class CreateEmployee {

    private static String URI = "https://innopolispython.onrender.com";
    private static String endpoint = "/employee";

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {
        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Ivan").position("QA").surname("Ivanov").build();
        String token = Authorization.getToken();

        int id = given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint).
                then().statusCode(201).
                extract().path("id");

        UsefulMethods.deleteEmployee(id);
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {
        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Ivan").position("QA").surname("Ivanov").build();
        String token = Authorization.getToken();

        int id = given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint).
                then().
                body("id", is(not(blankString()))).
                body("message", is("Employee created successfully")).
                extract().path("id");

        UsefulMethods.deleteEmployee(id);
    }

    @Test
    @DisplayName("Создать сотрудника без city")
    @Disabled("Есть актуальный баг")
    public void createEmployeeWithoutCityTest() {
        EmployeeRequest requestJSON = EmployeeRequest.builder().name("Ivan").position("QA").surname("Ivanov").build();
        String token = Authorization.getToken();

        Response response = given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint);

        System.out.println(response.prettyPrint());

        int id = response.path("id");

        UsefulMethods.deleteEmployee(id);
    }

    @Test
    @DisplayName("Создать сотрудника без name")
    public void createEmployeeWithoutNameTest() {
        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").position("QA").surname("Ivanov").build();
        String token = Authorization.getToken();

        ErrorResponse actualErrorResponse = given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint).
                then().
                extract().as(ErrorResponse.class);

        List<String> array = new ArrayList<>();
        array.add("name");
        ErrorResponse expectedErrorResponse = new ErrorResponse("Missing required fields", array);

        assertThat(actualErrorResponse).isEqualTo(expectedErrorResponse);
    }

    @Test
    @DisplayName("Создать сотрудника без surname и position")
    public void createEmployeeWithoutSurnamePositionTest() {
        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Ivan").build();
        String token = Authorization.getToken();

        ErrorResponse actualErrorResponse = given().baseUri(URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().post(endpoint).
                then().
                extract().as(ErrorResponse.class);

        List<String> array = new ArrayList<>();
        array.add("surname");
        array.add("position");
        ErrorResponse expectedErrorResponse = new ErrorResponse("Missing required fields", array);

        assertThat(actualErrorResponse).isEqualTo(expectedErrorResponse);
    }
}
