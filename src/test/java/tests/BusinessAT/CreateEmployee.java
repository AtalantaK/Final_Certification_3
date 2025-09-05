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

@DisplayName("Business AT. Создание нового сотрудника")
public class CreateEmployee {

    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    @DisplayName("Создание нового сотрудника")
    public void createEmployee() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        //Создаем сотрудника через API
        int employeeId = UsefulMethods.createEmployee("Samara", "Kseniia", "AQA", "Kalashnikova").path("id");
        EmployeeResponse employeeResponse = new EmployeeResponse("Samara", employeeId, "Kseniia", "AQA", "Kalashnikova");

        //Ищем в БД нашего созданного сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeResponse.getId()); //ищем сущность по первичному ключу
        entityManager.remove(employeeDB); //удаляем сущность
        entityManager.getTransaction().commit();

        assertThat(employeeResponse).isEqualTo(employeeDB);
    }
}
