package co.ke.test.users.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.ke.test.users.entities.Address;
import co.ke.test.users.entities.Users;
import co.ke.test.users.enums.Roles;
import co.ke.test.users.enums.Status;
import co.ke.test.users.models.AddressModel;
import co.ke.test.users.models.UserModel;
import co.ke.test.users.repository.UserRepository;
import co.ke.test.users.response.AddressResponse;
import co.ke.test.users.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public ResponseEntity<UserResponse> createOrUpdateUser(UserModel model) {
	    Users user = null;
	    
	    if (model.getUserId() != null && model.getUserId() > 0) {
	        user = userRepository.findById(model.getUserId()).orElse(null);
	        if (user == null) {
	            throw new NoSuchElementException("User not found: " + model.getUserId());
	        }
	    }

	    if (user == null) {
	        user = new Users();
	        user.setStatus(Status.ACTIVE);
	        user.setDateCreated(LocalDateTime.now());
	    }
	    
	    user.setDateUpdated(LocalDateTime.now());
	    user.setFirstName(model.getFirstName());
	    user.setLastName(model.getLastName());  
	    user.setEmail(model.getEmail());
	    user.setPhoneNumber(model.getPhoneNumer());
	    
	    if (model.getRole() != null) {
	        user.setRole(model.getRole());
	    }
	    
	    if (user.getUserId() != null && !user.getAddresses().isEmpty()) {
	        user.getAddresses().clear();
	    }
	    
	    if (model.getUserAddresses() != null && !model.getUserAddresses().isEmpty()) {
	        for (AddressModel m : model.getUserAddresses()) {
	            Address address = new Address();
	            address.setCity(m.getCity());
	            address.setCountry(m.getCountry());
	            address.setPostalCode(m.getPostalCode());
	            address.setPrimary(m.isPrimary());
	            address.setState(m.getState());
	            address.setStreet(m.getStreet());
	            address.setUser(user);
	            user.getAddresses().add(address);
	        }
	    }

	    Users savedUser = userRepository.save(user);
	    UserResponse response = mapToUserResponse(savedUser);
	    return ResponseEntity.ok(response);
	}


	public Page<UserResponse> getUsersByStatus(Status status, int page, int size, String searchTerm) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Users> usersPage = null;
		if (searchTerm != null && !searchTerm.isEmpty()) {
			usersPage = userRepository.getUsersbyStatus(status, searchTerm, pageable);
		} else {
			usersPage = userRepository.findByStatusOrderByUserIdDesc(status, pageable);
		}
		Page<UserResponse> responsePage = usersPage.map(this::mapToUserResponse);
		return responsePage;
	}

	public Page<UserResponse> getUsersByStatusAndRole(Status status, Roles role, int page, int size,
			String searchTerm) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Users> usersPage = null;
		if (searchTerm != null && !searchTerm.isEmpty()) {
			usersPage = userRepository.getUsersbyStatusRoles(status, role, searchTerm, pageable);
		} else {
			usersPage = userRepository.findByStatusAndRoleOrderByUserIdDesc(status, role, pageable);
		}
		Page<UserResponse> responsePage = usersPage.map(this::mapToUserResponse);
		return responsePage;
	}

	@Transactional
	public ResponseEntity<UserResponse> deleteUser(Long userId) {
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

		user.setStatus(Status.INACTIVE);
		user.setDateUpdated(LocalDateTime.now());

		Users updatedUser = userRepository.save(user);
		UserResponse response = mapToUserResponse(updatedUser);

		return ResponseEntity.ok(response);
	}
	

	private UserResponse mapToUserResponse(Users user) {
		List<AddressResponse> addressResponses = user.getAddresses().stream().map(this::mapToAddressResponse)
				.collect(Collectors.toList());

		return UserResponse.builder().userId(user.getUserId()).email(user.getEmail()).firstName(user.getFirstName())
				.lastName(user.getLastName()).role(user.getRole()).status(user.getStatus())
				.userAddresses(addressResponses).build();
	}

	private AddressResponse mapToAddressResponse(Address address) {
		return AddressResponse.builder().addressId(address.getAddressId()).street(address.getStreet())
				.city(address.getCity()).state(address.getState()).postalCode(address.getPostalCode())
				.country(address.getCountry()).primary(address.isPrimary()).build();
	}
}
