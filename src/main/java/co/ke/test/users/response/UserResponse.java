package co.ke.test.users.response;

import java.util.ArrayList;
import java.util.List;

import co.ke.test.users.enums.Roles;
import co.ke.test.users.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private Long userId;
	private String email;
	private String firstName;
	private String lastName;

	private Roles role;
	private Status status;
	List<AddressResponse> userAddresses = new ArrayList<>();

}



