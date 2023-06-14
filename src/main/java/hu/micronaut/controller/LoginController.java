package hu.micronaut.controller;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "login controller")
@Controller("/")
public interface LoginController {

    @Post("login")
    BearerAccessRefreshToken login(@Body UsernamePasswordCredentials credentials);

}
