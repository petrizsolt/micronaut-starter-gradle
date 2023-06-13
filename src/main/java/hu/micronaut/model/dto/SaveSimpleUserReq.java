package hu.micronaut.model.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveSimpleUserReq {

    @NotBlank(message = "Name field is required!")
    String name;

    LocalDate birthDate;

    @NotBlank(message = "Username is required!")
    String username;

    @NotBlank(message = "Password is required!")
    String password;
}
