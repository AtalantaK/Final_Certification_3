package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class ErrorResponse {

    @JsonProperty("error")
    private String error;

    @JsonProperty("missing_fields")
    private List<String> missingFields;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, List<String> missingFields) {
        this.error = error;
        this.missingFields = missingFields;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error='" + error + '\'' +
                ", missingFields=" + missingFields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(error, that.error) && Objects.equals(missingFields, that.missingFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, missingFields);
    }
}
