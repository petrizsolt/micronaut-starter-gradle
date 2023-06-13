package hu.micronaut.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.model.entitys.UserRoles;
import hu.micronaut.repository.RoleRepository;
import hu.micronaut.repository.SimpleUserRepository;
import hu.micronaut.security.RoleCodes;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Requires(property = "load.h2.init.data", value = "true")
@Singleton
public class InitH2DatabaseTest implements ApplicationEventListener<ServerStartupEvent> {

    private final SimpleUserRepository simpleUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        List<UserRoles> userRoles = new ArrayList<>();
        userRoles.add(new UserRoles(1L, RoleCodes.ADMIN, "admin user"));
        userRoles.add(new UserRoles(2L, RoleCodes.USER, "default user role."));
        roleRepository.saveAll(userRoles);

        SimpleUser firstUser = new SimpleUser();
        firstUser.setPassword(BCrypt.withDefaults().hashToString(12, "admin".toCharArray()));
        firstUser.setName("First Admin user");
        firstUser.setCreationTime(LocalDateTime.now());
        firstUser.setBirthDate(LocalDate.of(1991, 8, 15));
        firstUser.setUsername("admin");
        firstUser.setRoles(List.of(userRoles.get(0)));
        simpleUserRepository.save(firstUser);

        log.info("H2 database data loaded successfully!");
    }
}
