package hu.micronaut.controller;

import hu.micronaut.model.dto.SaveSimpleUserReq;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.service.UserService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;


@Controller("/api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Get(uri = "/get-by-id/{id}", produces = MediaType.APPLICATION_JSON)
	public SimpleUser getUsers(@PathVariable(name = "id") Long id) {
		return userService.findById(id);
	}

	@Get(uri = "/get-all-users", produces = MediaType.APPLICATION_JSON)
	public Page<SimpleUser> getUsers(
			@QueryValue(defaultValue = "0") Integer page,
			@QueryValue(defaultValue = "5") Integer size) {
		return userService.getAllUsers(Pageable.from(page, size));
	}

	@Delete(uri = "/delete-by-id/{id}", produces = MediaType.APPLICATION_JSON)
	public void deleteUser(@PathVariable(name = "id") Long id) {
		userService.deleteById(id);
	}

	@Status(HttpStatus.CREATED)
	@Post(uri = "/save-user", produces = MediaType.APPLICATION_JSON)
	public SimpleUser saveUser(@Body SaveSimpleUserReq req) {
		return userService.saveUser(req);
	}
}
