package co.ke.test.users.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AddressResponse {
	private Long addressId;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private boolean primary;

}
