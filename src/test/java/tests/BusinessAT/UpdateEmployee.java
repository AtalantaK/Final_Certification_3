package tests.BusinessAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import helpers.UsefulMethodsAPI;
import helpers.UsefulMethodsDB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Business AT. Обновить информацию о сотруднике")
public class UpdateEmployee {

    @Test
    @DisplayName("Обновить сотрудника полностью")
    public void updateEmployeeCompletely() {

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        int employeeId = UsefulMethodsDB.createEmployeeDB(employee);

        //Обновляем сотрудника через API
        UsefulMethodsAPI.updateEmployeeCompletelyAPI(employeeId, "Moscow", "Xenia", "AQA", "Ivanova");
        EmployeeResponse employeeResponse = new EmployeeResponse("Moscow", employeeId, "Xenia", "AQA", "Ivanova");

        //Ищем в БД нашего обновленного сотрудника
        EmployeeResponse employeeDB = UsefulMethodsDB.getEmployeeDB(employeeId);

        UsefulMethodsDB.deleteEmployeeDB(employeeDB);

        assertThat(employeeResponse).isEqualTo(employeeDB);

    }

    @Test
    @DisplayName("Обновить сотрудника частично")
    public void updateEmployeePartially() {

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        int employeeId = UsefulMethodsDB.createEmployeeDB(employee);

        //Обновляем сотрудника через API
        UsefulMethodsAPI.updateEmployeeCityPositionAPI(employeeId, "Moscow", "AQA");
        EmployeeResponse employeeResponse = new EmployeeResponse("Moscow", employeeId, "Kseniia", "AQA", "Kalashnikova");

        //Ищем в БД нашего обновленного сотрудника
        EmployeeResponse employeeDB = UsefulMethodsDB.getEmployeeDB(employeeId);

        UsefulMethodsDB.deleteEmployeeDB(employeeDB);

        assertThat(employeeResponse).isEqualTo(employeeDB);

    }
}
