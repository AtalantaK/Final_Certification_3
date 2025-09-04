package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class ValidationErrorResponse {
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private String message;
    @JsonProperty("wrong_type_fields")
    private List<String> wrongTypeFields;

    public ValidationErrorResponse(String error, String message, List<String> wrongTypeFields) {
        this.error = error;
        this.message = message;
        this.wrongTypeFields = wrongTypeFields;
    }

    public ValidationErrorResponse() {
    }

    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", wrongTypeFields=" + wrongTypeFields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ValidationErrorResponse that = (ValidationErrorResponse) o;
        return Objects.equals(error, that.error) && Objects.equals(message, that.message) && Objects.equals(wrongTypeFields, that.wrongTypeFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, message, wrongTypeFields);
    }
}
