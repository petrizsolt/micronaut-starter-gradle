package hu.micronaut.controller;

import hu.micronaut.model.dto.ModifyUserRoleReq;
import hu.micronaut.model.dto.SaveSimpleUserReq;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.model.entitys.UserRoles;
import hu.micronaut.security.RoleCodes;
import hu.micronaut.service.UserService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Tag(name = "Users controller")
@Controller("/api/user")
@RequiredArgsConstructor
@Secured(IS_AUTHENTICATED)
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


	@ApiResponse(responseCode = "300",
			description = "Multiple user found with the supplied 'name'. Returns a list of users found.")
	@ApiResponse(responseCode = "200",
			description = "Exactly found a user with supplied name. Successfully deleted them.")
	@Delete(uri = "/delete-by-name/{name}", produces = MediaType.APPLICATION_JSON)
	public HttpResponse<List<SimpleUser>> deleteByName(@PathVariable(name = "name") String name) {
		return userService.deleteByName(name);
	}

	@Put(uri = "/update-user/{userId}", produces = MediaType.APPLICATION_JSON)
	public SimpleUser modifyUser(@Body SaveSimpleUserReq req, @PathVariable(name = "userId") Long userId) {
		return userService.modifyUser(req, userId);
	}

	@Get(uri = "/get-all-roles", produces = MediaType.APPLICATION_JSON)
	public List<UserRoles> getRoles() {
		return userService.getAllRoles();
	}

	@Secured(RoleCodes.ADMIN)
	@Put(uri = "/change-user-roles")
	public SimpleUser changeUserRoles(@Body ModifyUserRoleReq req) {
		return userService.changeUserRoles(req);
	}
}
