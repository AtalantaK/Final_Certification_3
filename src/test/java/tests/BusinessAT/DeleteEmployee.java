package tests.BusinessAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import helpers.UsefulMethodsAPI;
import helpers.UsefulMethodsDB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Business AT. Удаление сотрудника")
public class DeleteEmployee {

    @Test
    @DisplayName("Удаление сотрудника")
    public void deleteEmployee() {

        //Создаем сотрудника
        EmployeeRequest expectedEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        int employeeId = UsefulMethodsDB.createEmployeeDB(expectedEmployee);

        UsefulMethodsAPI.deleteEmployeeAPI(employeeId);

        //Ищем в БД нашего удалённого сотрудника
        EmployeeResponse actualEmployee = UsefulMethodsDB.getEmployeeDB(employeeId);

        assertThat(actualEmployee).isNull();
    }
}
