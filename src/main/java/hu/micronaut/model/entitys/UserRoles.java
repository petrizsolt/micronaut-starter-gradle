package hu.micronaut.model.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLES")
public class UserRoles {

    @Id
    private Long id;

    @Column(unique = true)
    private String roleCode;

    private String roleDescription;

}
