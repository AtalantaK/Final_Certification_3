package tests.BusinessAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import helpers.EnvHelper;
import helpers.UsefulMethods;
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
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Business AT. Обновить информацию о сотруднике")
public class UpdateEmployee {
    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    @DisplayName("Обновить сотрудника полностью")
    public void updateEmployeeCompletely() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        entityManager.getTransaction().begin();
        entityManager.persist(employee); //сохраняем сущность в БД
        entityManager.getTransaction().commit();

        int employeeId = employee.getId();

        entityManager.clear(); // очищаем контекст

        //Обновляем сотрудника через API
        UsefulMethods.updateEmployeeCompletely(employeeId, "Moscow", "Xenia", "AQA", "Ivanova");
        EmployeeResponse employeeResponse = new EmployeeResponse("Moscow", employeeId, "Xenia", "AQA", "Ivanova");

        //Ищем в БД нашего обновленного сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeId); //ищем сущность по первичному ключу
        entityManager.remove(employeeDB); //удаляем сущность
        entityManager.getTransaction().commit();

        assertThat(employeeResponse).isEqualTo(employeeDB);

    }

    @Test
    @DisplayName("Обновить сотрудника частично")
    public void updateEmployeePartially() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("Senior QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        entityManager.getTransaction().begin();
        entityManager.persist(employee); //сохраняем сущность в БД
        entityManager.getTransaction().commit();

        int employeeId = employee.getId();

        entityManager.clear(); // очищаем контекст

        //Обновляем сотрудника через API
        UsefulMethods.updateEmployeeCityPosition(employeeId, "Moscow", "AQA");
        EmployeeResponse employeeResponse = new EmployeeResponse("Moscow", employeeId, "Kseniia", "AQA", "Kalashnikova");

        //Ищем в БД нашего обновленного сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeId); //ищем сущность по первичному ключу
        entityManager.remove(employeeDB); //удаляем сущность
        entityManager.getTransaction().commit();

        assertThat(employeeResponse).isEqualTo(employeeDB);

    }
}
