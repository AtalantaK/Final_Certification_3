package entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeRequest {
    private String city; //опциональное поле
    private String name;
    private String position;
    private String surname;
}
