package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ResponseMessage {
    @JsonProperty("id")
    private int id;
    @JsonProperty("message")
    private String message;

    public ResponseMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public ResponseMessage() {
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessage that = (ResponseMessage) o;
        return id == that.id && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }
}
