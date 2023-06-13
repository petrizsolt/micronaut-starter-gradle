package hu.micronaut.repository;

import hu.micronaut.model.entitys.UserRoles;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRoles, Long> {
    List<UserRoles> findAllByIdIn(List<Long> ids);
}
