package hu.micronaut.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.micronaut.exceptions.exception.UserNotFoundException;
import hu.micronaut.model.dto.ModifyUserRoleReq;
import hu.micronaut.model.dto.SaveSimpleUserReq;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.model.entitys.UserRoles;
import hu.micronaut.repository.RoleRepository;
import hu.micronaut.repository.SimpleUserRepository;
import hu.micronaut.utility.Mapper;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;


@Singleton
@RequiredArgsConstructor
public class UserService {

    private final SimpleUserRepository simpleUserRepository;
    private final RoleRepository roleRepository;

    public Page<SimpleUser> getAllUsers(Pageable pageable) {
        return simpleUserRepository.findAll(pageable);
    }

    @Transactional
    public SimpleUser saveUser(SaveSimpleUserReq req) {
        SimpleUser user = Mapper.map(req, SimpleUser.class);
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, req.getPassword().toCharArray());
        user.setPassword(bcryptHashString);
        return simpleUserRepository.save(user);
    }

    @Transactional
    public SimpleUser modifyUser(SaveSimpleUserReq req, Long userId) {
        SimpleUser user = findById(userId);

        user = Mapper.map(req, user);

        return simpleUserRepository.save(user);
    }

    public SimpleUser findById(Long id) {
        return simpleUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteById(Long id) {
        SimpleUser usr = findById(id);
        simpleUserRepository.delete(usr);
    }

    public HttpResponse<List<SimpleUser>> deleteByName(String name) {
        List<SimpleUser> usersFound = simpleUserRepository.findAllByName(name);

        if(usersFound.isEmpty()) {
            throw new UserNotFoundException("No user found with name: " + name);
        }

        if(usersFound.size() > 1) {
            return HttpResponse
                    .status(HttpStatus.MULTIPLE_CHOICES)
                    .body(usersFound);
        }

        simpleUserRepository.delete(usersFound.get(0));
        return HttpResponse.ok(usersFound);
    }

    public List<UserRoles> getAllRoles() {
        return (List<UserRoles>) roleRepository.findAll();
    }

    @Transactional
    public SimpleUser changeUserRoles(ModifyUserRoleReq req) {
        SimpleUser user = simpleUserRepository.findById(req.getUserId())
                .orElseThrow(() -> new UserNotFoundException(req.getUserId()));

        List<UserRoles> roles = roleRepository.findAllByIdIn(req.getRoleIds());

        user.setRoles(roles);

        return simpleUserRepository.save(user);
    }
}
