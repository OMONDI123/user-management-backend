package co.ke.test.users.models;

import java.util.ArrayList;
import java.util.List;

import co.ke.test.users.enums.Roles;

import lombok.Data;

@Data
public class UserModel {
	private Long userId;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumer;

	private Roles role;

	List<AddressModel> userAddresses = new ArrayList<>();

}

