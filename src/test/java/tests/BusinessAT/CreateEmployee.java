package tests.BusinessAT;

import entities.EmployeeResponse;
import helpers.EnvHelper;
import helpers.UsefulMethodsAPI;
import helpers.UsefulMethodsDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import manager.MyPUI;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Business AT. Создание нового сотрудника")
public class CreateEmployee {

    @Test
    @DisplayName("Создание нового сотрудника")
    public void createEmployee() {

        //Создаем сотрудника через API
        int employeeId = UsefulMethodsAPI.createEmployeeAPI("Samara", "Kseniia", "AQA", "Kalashnikova").path("id");
        EmployeeResponse employeeResponse = new EmployeeResponse("Samara", employeeId, "Kseniia", "AQA", "Kalashnikova");

        //Ищем в БД нашего созданного сотрудника
        EmployeeResponse employeeDB = UsefulMethodsDB.getEmployeeDB(employeeId);

        UsefulMethodsDB.deleteEmployeeDB(employeeDB);

        assertThat(employeeResponse).isEqualTo(employeeDB);
    }
}
