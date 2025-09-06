package tests.BusinessAT;

import entities.EmployeeResponse;
import helpers.UsefulMethodsAPI;
import helpers.UsefulMethodsDB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Business AT. Получение списка всех сотрудников")
public class GetEmployees {

    @Test
    @DisplayName("Получение списка всех сотрудников")
    public void getEmployees() {

        //Получаем всех сотрудников через API
        List<EmployeeResponse> employeesResponse = UsefulMethodsAPI.getEmployeesAPI().jsonPath().getList(".", EmployeeResponse.class);

        //Ищем в БД наших сотрудников
        List<EmployeeResponse> employeesDB = UsefulMethodsDB.getEmployeesDB();

        assertThat(employeesResponse).isEqualTo(employeesDB);

    }
}
