package hu.micronaut.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {

    String code;

    String message;

    LocalDateTime date;
}
