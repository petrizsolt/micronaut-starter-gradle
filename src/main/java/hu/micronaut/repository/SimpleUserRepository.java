package hu.micronaut.repository;

import hu.micronaut.model.entitys.SimpleUser;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
    List<SimpleUser> findAllByName(String name);
}
