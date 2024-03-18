package shared_backend.used_stuff.dto.user;

import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;

@Data
public class JoinResponseDto {
	private String username;
	private String password;
	private String name;
	private int age;
	private Address address;
	private Gender gender;

	public JoinResponseDto(Password password, Profile profile) {
		this.username = password.getUsername();
		this.password = password.getPassword();
		this.name = profile.getName();
		this.age = profile.getAge();
		this.address = profile.getAddress();
		this.gender = profile.getGender();
	}
}
