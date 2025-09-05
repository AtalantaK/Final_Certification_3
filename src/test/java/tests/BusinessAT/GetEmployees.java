package tests.BusinessAT;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import helpers.EnvHelper;
import helpers.UsefulMethods;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.spi.PersistenceUnitInfo;
import manager.MyPUI;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Business AT. Получение списка всех сотрудников")
public class GetEmployees {
    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    @DisplayName("Получение списка всех сотрудников")
    public void getEmployees() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        //Получаем всех сотрудников через API
        List<EmployeeResponse> employeesResponse = UsefulMethods.getEmployees().jsonPath().getList(".", EmployeeResponse.class);

        //Ищем в БД нашего обновленного сотрудника
        entityManager.getTransaction().begin();
        TypedQuery<EmployeeResponse> query = entityManager.createQuery("SELECT em FROM EmployeeResponse em", EmployeeResponse.class); // указывать НЕ ИМЯ таблицы, а имя класса!
        List<EmployeeResponse> employeesDB = query.getResultList();
        entityManager.getTransaction().commit();

        assertThat(employeesResponse).isEqualTo(employeesDB);

    }
}
