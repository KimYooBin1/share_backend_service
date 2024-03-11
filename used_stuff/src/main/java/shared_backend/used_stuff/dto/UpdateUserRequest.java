package shared_backend.used_stuff.dto;

import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;

@Data
public class UpdateUserRequest {
	private String password;
	private String name;
	private Address address;
	private int age;
	private Gender gender;
}
