package entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "employee", schema = "public", catalog = "employee_postgres")
public class EmployeeRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "city")
    private String city; //опциональное поле

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "surname")
    private String surname;

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getSurname() {
        return surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRequest request = (EmployeeRequest) o;
        return id == request.id && Objects.equals(city, request.city) && Objects.equals(name, request.name) && Objects.equals(position, request.position) && Objects.equals(surname, request.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, name, position, surname);
    }

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
