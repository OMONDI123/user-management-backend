package co.ke.test.users.models;

import lombok.Data;

@Data
public class AddressModel {

	private Long addressId;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private boolean primary;

}
