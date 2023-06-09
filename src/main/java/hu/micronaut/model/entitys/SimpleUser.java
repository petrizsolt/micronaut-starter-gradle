package hu.micronaut.model.entitys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SIMPLE_USERS")
public class SimpleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private LocalDateTime creationTime;

    private LocalDateTime modifyDate;

    @PrePersist
    public void autoSetCreationDateOnInsert() {
        this.creationTime = LocalDateTime.now();
    }

    @PreUpdate
    public void autoSetModifyDateOnUpdate() {
        this.modifyDate = LocalDateTime.now();
    }
}
