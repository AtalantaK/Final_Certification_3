package tests.ContractAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import entities.ResponseMessage;
import entities.ValidationErrorResponse;
import helpers.Authorization;
import helpers.Endpoints;
import helpers.UsefulMethodsDB;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Обновить информацию о сотруднике")
public class UpdateEmployee {
//    private static final Log log = LogFactory.getLog(UpdateEmployee.class);

    @BeforeAll
    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    @DisplayName("Проверить код ответа")
    public void checkResponseCodeTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Xenia").position("AQA").surname("Ivanova").build();

        String token = Authorization.getToken();

        given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().statusCode(200);


        EmployeeResponse employeeResponse = new EmployeeResponse(requestJSON.getCity(), employeeId, requestJSON.getName(), requestJSON.getPosition(), requestJSON.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Проверить тело ответа")
    public void checkResponseBodyTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Xenia").position("Senior QA").surname("Ivanova").build();
        String token = Authorization.getToken();

        ResponseMessage expectedResponseMessage = new ResponseMessage(employeeId, "Employee updated successfully");

        ResponseMessage actualResponseMessage = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().extract().as(ResponseMessage.class);

        assertThat(actualResponseMessage).isEqualTo(expectedResponseMessage);

        EmployeeResponse employeeResponse = new EmployeeResponse(requestJSON.getCity(), employeeId, requestJSON.getName(), requestJSON.getPosition(), requestJSON.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Ошибка валидации данных")
    public void validationErrorTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        String requestJSON = "{\n" +
                "    \"city\": 123,\n" +
                "    \"name\": 123,\n" +
                "    \"position\": 123,\n" +
                "    \"surname\": 123\n" +
                "}";

        String token = Authorization.getToken();

        List<String> wrongTypeFields = new ArrayList<>();
        wrongTypeFields.add("city");
        wrongTypeFields.add("name");
        wrongTypeFields.add("position");
        wrongTypeFields.add("surname");

        ValidationErrorResponse expectedResponseMessage = new ValidationErrorResponse("Invalid field types", "All fields must be strings", wrongTypeFields);

        ValidationErrorResponse actualResponseMessage = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().
                statusCode(400).
                extract().as(ValidationErrorResponse.class);

        assertThat(actualResponseMessage).isEqualTo(expectedResponseMessage);

        EmployeeResponse employeeResponse = new EmployeeResponse(initialEmployee.getCity(), employeeId, initialEmployee.getName(), initialEmployee.getPosition(), initialEmployee.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Обновление несуществующего сотрудника")
    public void updateNonExistenceEmployeeTest() {

        int employeeId = 123456789;

        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").name("Kseniia").position("AQA").surname("Kalashnikova").build();
        String token = Authorization.getToken();

        given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().statusCode(404).body("error", is("Employee with id '" + employeeId + "' not found"));
    }

    @Test
    @DisplayName("Обновить только Город")
    public void updateCityTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        EmployeeRequest requestJSON = EmployeeRequest.builder().city("Moscow").build();
        String token = Authorization.getToken();

        ResponseMessage expectedResponseMessage = new ResponseMessage(employeeId, "Employee updated successfully");

        ResponseMessage actualResponseMessage = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().extract().as(ResponseMessage.class);

        assertThat(actualResponseMessage).isEqualTo(expectedResponseMessage);

        EmployeeResponse employeeResponse = new EmployeeResponse(requestJSON.getCity(), employeeId, requestJSON.getName(), requestJSON.getPosition(), requestJSON.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Обновить только Имя")
    public void updateNameTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        EmployeeRequest requestJSON = EmployeeRequest.builder().name("Ivan").build();
        String token = Authorization.getToken();

        ResponseMessage expectedResponseMessage = new ResponseMessage(employeeId, "Employee updated successfully");

        ResponseMessage actualResponseMessage = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().extract().as(ResponseMessage.class);

        assertThat(actualResponseMessage).isEqualTo(expectedResponseMessage);

        EmployeeResponse employeeResponse = new EmployeeResponse(requestJSON.getCity(), employeeId, requestJSON.getName(), requestJSON.getPosition(), requestJSON.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Обновить только Позицию")
    public void updatePositionTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        EmployeeRequest requestJSON = EmployeeRequest.builder().position("AQA").build();
        String token = Authorization.getToken();

        ResponseMessage expectedResponseMessage = new ResponseMessage(employeeId, "Employee updated successfully");

        ResponseMessage actualResponseMessage = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().extract().as(ResponseMessage.class);

        assertThat(actualResponseMessage).isEqualTo(expectedResponseMessage);

        EmployeeResponse employeeResponse = new EmployeeResponse(requestJSON.getCity(), employeeId, requestJSON.getName(), requestJSON.getPosition(), requestJSON.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }

    @Test
    @DisplayName("Обновить только Фамилию")
    public void updateSurnameTest() {

        EmployeeRequest initialEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();
        int employeeId = UsefulMethodsDB.createEmployeeDB(initialEmployee);

        EmployeeRequest requestJSON = EmployeeRequest.builder().surname("Ivanova").build();
        String token = Authorization.getToken();

        ResponseMessage expectedResponseMessage = new ResponseMessage(employeeId, "Employee updated successfully");

        ResponseMessage actualResponseMessage = given().baseUri(Endpoints.URI).
                body(requestJSON).contentType(ContentType.JSON).
                auth().oauth2(token).
                log().all().
                when().put(Endpoints.EMPLOYEE + "/" + employeeId).
                then().extract().as(ResponseMessage.class);

        assertThat(actualResponseMessage).isEqualTo(expectedResponseMessage);

        EmployeeResponse employeeResponse = new EmployeeResponse(requestJSON.getCity(), employeeId, requestJSON.getName(), requestJSON.getPosition(), requestJSON.getSurname());
        UsefulMethodsDB.deleteEmployeeDB(employeeResponse);
    }
}
