package shared_backend.used_stuff.dto;

import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;

@Data
public class JoinRequestDto {
	String username;
	String password;
	private String name;
	private int age;
	private Address address;
	private Gender gender;
}
