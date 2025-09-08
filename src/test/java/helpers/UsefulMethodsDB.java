package helpers;

import entities.EmployeeRequest;
import entities.EmployeeResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.spi.PersistenceUnitInfo;
import manager.MyPUI;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.IOException;
import java.util.List;
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
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(employee); //сохраняем сущность в БД
        entityManager.flush(); // заставляем Hibernate синхронизировать состояние с БД
        int employeeId = employee.getId();
        transaction.commit();

        System.out.println("Я создал в БД сотрудника с id = " + employeeId);

        entityManager.close();

        return employeeId;
    }

    public static void deleteEmployeeDB(EmployeeResponse employee) {

        entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //Удаляем из БД нашего сотрудника
        transaction.begin();
        EmployeeResponse foundEmployee = entityManager.find(EmployeeResponse.class, employee.getId());
        entityManager.remove(foundEmployee);
        transaction.commit();

        System.out.println("Я удалил в БД сотрудника с id = " + employee.getId());

        entityManager.close();
    }

    public static EmployeeResponse getEmployeeDB(int employeeId) {

        entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //Ищем в БД нашего сотрудника
        transaction.begin();
        EmployeeResponse employeeDB = entityManager.find(EmployeeResponse.class, employeeId); //ищем сущность по первичному ключу
        transaction.commit();

        System.out.println("Я нашел в БД сотрудника с id = " + employeeId);
        System.out.println("Вот его данные: ");
        System.out.println(employeeDB);

        entityManager.close();

        return employeeDB;
    }

    public static List<EmployeeResponse> getEmployeesDB() {

        entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        TypedQuery<EmployeeResponse> query = entityManager.createQuery("SELECT em FROM EmployeeResponse em", EmployeeResponse.class); // указывать НЕ ИМЯ таблицы, а имя класса!
        List<EmployeeResponse> employeesDB = query.getResultList();
        transaction.commit();

        System.out.println("Я нашел всех сотрудников в БД");

        entityManager.close();

        return employeesDB;
    }
}
