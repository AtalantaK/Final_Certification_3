package tests.BusinessAT;

import entities.EmployeeRequest;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Business AT. Удаление сотрудника")
public class DeleteEmployee {

    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    public void deleteEmployee() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        //Создаем сотрудника
        EmployeeRequest expectedEmployee = EmployeeRequest.builder().city("Samara").name("Kseniia").position("QA").surname("Kalashnikova").build();

        //Вставляем сотрудника в БД
        entityManager.getTransaction().begin();
        entityManager.persist(expectedEmployee); //сохраняем сущность в БД
        entityManager.getTransaction().commit();

        int employeeId = expectedEmployee.getId();

        UsefulMethods.deleteEmployee(employeeId);

        entityManager.clear(); // очищаем контекст

        //Ищем в БД нашего удалённого сотрудника
        entityManager.getTransaction().begin();
        EmployeeRequest actualEmployee = entityManager.find(EmployeeRequest.class, employeeId);  //ищем сущность по первичному ключу
        entityManager.getTransaction().commit();

        assertThat(actualEmployee).isNull();
    }
}
