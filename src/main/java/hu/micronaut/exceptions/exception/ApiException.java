package hu.micronaut.exceptions.exception;

import io.micronaut.http.annotation.Get;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException {
    private String code = "API_EXCEPTION";
    private String message = "Generic api Exception!";
    private LocalDateTime date;

    public ApiException(String message) {
        super(message);
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public ApiException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
        this.date = LocalDateTime.now();
    }

    public ApiException() {
        super();
        this.date = LocalDateTime.now();
    }

}
