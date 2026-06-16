package co.ke.test.users.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.test.users.enums.Roles;
import co.ke.test.users.enums.Status;
import co.ke.test.users.models.UserModel;
import co.ke.test.users.response.UserResponse;
import co.ke.test.users.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("createOrUpdateUser")
	public ResponseEntity<UserResponse> createOrUpdateUser(@RequestBody UserModel model) {
		return userService.createOrUpdateUser(model);
	}

	@GetMapping("getUsersByStatus")
	public Page<UserResponse> getUsers(@RequestParam(value = "status", required = true) Status status,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String searchTerm) {
		return userService.getUsersByStatus(status, page, size, searchTerm);
	}

	@GetMapping("/getUsersByStatusAndRole")
	public Page<UserResponse> getUsersByStatusAndRole(@RequestParam("status") Status status,
			@RequestParam("role") Roles role, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String searchTerm) {
		return userService.getUsersByStatusAndRole(status, role, page, size, searchTerm);
	}
	@GetMapping("/getRoleEnum")
	public List<Roles> getRoleEnum(){
		return Arrays.asList(Roles.values());
	}@GetMapping("/getStatus")
	public List<Status> getStatus(){
		return Arrays.asList(Status.values());
	}


}
