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

@DisplayName("Business AT. Получить сотрудника по имени")
public class GetEmployeeByName {
    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    public void getEmployeeByName() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        String employeeName = "Kseniia";

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name(employeeName).position("QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        entityManager.getTransaction().begin();
        entityManager.persist(employee); //сохраняем сущность в БД
        entityManager.getTransaction().commit();

        int employeeId = employee.getId();

        EmployeeResponse employeeResponse = UsefulMethods.getEmployeeByName(employeeName).as(EmployeeResponse.class);

        entityManager.clear(); // очищаем контекст

        //Ищем в БД нашего созданного сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeId); //ищем сущность по первичному ключу
        entityManager.getTransaction().commit();

        entityManager.clear(); // очищаем контекст

        //Удаляем за собой созданного сотрудника из БД
        entityManager.getTransaction().begin();
        entityManager.remove(employee); //удаляем сущность по первичному ключу
        entityManager.getTransaction().commit();

        assertThat(employeeResponse.getName()).isEqualTo(employeeDB.getName());
    }
}
