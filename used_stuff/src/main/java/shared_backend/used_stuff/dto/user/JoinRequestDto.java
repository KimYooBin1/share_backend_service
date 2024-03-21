package shared_backend.used_stuff.dto.user;

import static lombok.AccessLevel.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;

@Data
@NoArgsConstructor(access = PROTECTED)
public class JoinRequestDto {
	@NotNull
	String username;
	@NotNull
	String password;
	@NotNull
	private String name;
	@NotNull
	private Integer age;
	@NotNull
	private Address address;
	@NotNull
	private Gender gender;

	public JoinRequestDto(String username, String password, String name, Integer age, Address address, Gender gender) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
		this.address = address;
		this.gender = gender;
	}
}
