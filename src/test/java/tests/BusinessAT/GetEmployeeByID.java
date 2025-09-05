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

@DisplayName("Business AT. Получить сотрудника по ID")
public class GetEmployeeByID {
    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    @DisplayName("Получить сотрудника по ID")
    public void getEmployeeByID() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        //Создаем сотрудника
        EmployeeRequest employee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        entityManager.getTransaction().begin();
        entityManager.persist(employee); //сохраняем сущность в БД
        entityManager.getTransaction().commit();

        int employeeId = employee.getId();

        EmployeeResponse employeeResponse = UsefulMethods.getEmployeeByID(employeeId).as(EmployeeResponse.class);

        entityManager.clear(); // очищаем контекст

        //Ищем в БД нашего созданного сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeId); //ищем сущность по первичному ключу
        entityManager.remove(employeeDB); //удаляем сущность
        entityManager.getTransaction().commit();

        assertThat(employeeResponse).isEqualTo(employeeDB);
    }
}
