package shared_backend.used_stuff.dto.user;

import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;

@Data
public class UserResponseDto {
	private String username;
	private String name;
	private int age;
	private int point;
	private Address address;
	private Gender gender;

	public UserResponseDto(Password password, Profile profile, int point) {
		this.username = password.getUsername();
		this.name = profile.getName();
		this.age = profile.getAge();
		this.point = point;
		this.address = profile.getAddress();
		this.gender = profile.getGender();
	}
}
