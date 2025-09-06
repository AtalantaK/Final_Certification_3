package helpers;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import manager.MyPUI;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.IOException;
import java.util.Properties;

public class UsefulMethodsDB {

    private static EntityManager entityManager;
    private static EnvHelper envHelper;
    private static EntityManagerFactory emf = null;

    static {
        try {
            envHelper = new EnvHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
    }

    private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static int createEmployeeDB(EmployeeRequest employee) {

        entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(employee); //сохраняем сущность в БД
        entityManager.flush(); // заставляем Hibernate синхронизировать состояние с БД
        int employeeId = employee.getId();
        entityManager.getTransaction().commit();

        System.out.println("Я создал в БД сотрудника с id = " + employeeId);

        entityManager.close();

        return employeeId;
    }

    public static void deleteEmployeeDB(EmployeeResponse employee) {

        entityManager = getEntityManager();

        //Удаляем из БД нашего сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse foundEmployee = entityManager.find(EmployeeResponse.class, employee.getId());
        entityManager.remove(foundEmployee);
        entityManager.getTransaction().commit();

        System.out.println("Я удалил в БД сотрудника с id = " + employee.getId());

        entityManager.close();
    }

    public static EmployeeResponse getEmployeeDB(int employeeId) {

        entityManager = getEntityManager();

        //Ищем в БД нашего сотрудника
        entityManager.getTransaction().begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeId); //ищем сущность по первичному ключу
        entityManager.getTransaction().commit();

        System.out.println("Я нашел в БД сотрудника с id = " + employeeId);
        System.out.println("Вот его данные: ");
        System.out.println(employeeDB);

        entityManager.close();

        return employeeDB;
    }

    public static Response getEmployeesDB() {

        return null;
    }
}
