package tests.BusinessAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import helpers.UsefulMethodsAPI;
import helpers.UsefulMethodsDB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Business AT. Получить сотрудника по имени")
public class GetEmployeeByName {

    @Test
    @DisplayName("Получить сотрудника по имени")
    public void getEmployeeByName() {

        String employeeName = "Kseniia";

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name(employeeName).position("QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        int employeeId = UsefulMethodsDB.createEmployeeDB(employee);

        EmployeeResponse employeeResponse = UsefulMethodsAPI.getEmployeeByNameAPI(employeeName).as(EmployeeResponse.class);

        //Ищем в БД нашего созданного сотрудника
        EmployeeResponse employeeDB = UsefulMethodsDB.getEmployeeDB(employeeId);

        UsefulMethodsDB.deleteEmployeeDB(employeeDB);

        assertThat(employeeResponse.getName()).isEqualTo(employeeDB.getName());
    }
}
