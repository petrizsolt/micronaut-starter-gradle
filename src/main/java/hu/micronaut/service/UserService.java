package hu.micronaut.service;


import hu.micronaut.exceptions.exception.UserNotFoundException;
import hu.micronaut.model.dto.SaveSimpleUserReq;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.repository.SimpleUserRepository;
import hu.micronaut.utility.Mapper;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final SimpleUserRepository simpleUserRepository;

    public Page<SimpleUser> getAllUsers(Pageable pageable) {
        return simpleUserRepository.findAll(pageable);
    }

    public SimpleUser saveUser(SaveSimpleUserReq req) {
        SimpleUser user = Mapper.map(req, SimpleUser.class);
        user.setCreationTime(LocalDateTime.now());
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
}
