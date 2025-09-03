package entities;

import java.util.Objects;

public class EmployeeResponse {

    private String city;
    private int id;
    private String name;
    private String position;
    private String surname;

    public EmployeeResponse(String city, int id, String name, String position, String surname) {
        this.city = city;
        this.id = id;
        this.name = name;
        this.position = position;
        this.surname = surname;
    }

    public EmployeeResponse(int id, String name, String position, String surname) {

        new EmployeeResponse(null,id, name, position, surname);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "city='" + city + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeResponse employee = (EmployeeResponse) o;
        return id == employee.id && Objects.equals(city, employee.city) && Objects.equals(name, employee.name) && Objects.equals(position, employee.position) && Objects.equals(surname, employee.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, id, name, position, surname);
    }
}
