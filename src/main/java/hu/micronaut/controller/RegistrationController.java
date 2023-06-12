package hu.micronaut.controller;

import hu.micronaut.model.dto.SaveSimpleUserReq;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.service.UserService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
@Tag(name = "Signup controller")
@Controller("/api/signup")
@Secured(SecurityRule.IS_ANONYMOUS)
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @Status(HttpStatus.CREATED)
    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    public SimpleUser saveUser(@Body @Valid SaveSimpleUserReq req) {
        return userService.saveUser(req);
    }

}
