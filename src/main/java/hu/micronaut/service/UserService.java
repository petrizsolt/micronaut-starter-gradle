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
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final SimpleUserRepository simpleUserRepository;

    public Page<SimpleUser> getAllUsers(Pageable pageable) {
        return simpleUserRepository.findAll(pageable);
    }

    @Transactional
    public SimpleUser saveUser(SaveSimpleUserReq req) {
        SimpleUser user = Mapper.map(req, SimpleUser.class);
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
}
