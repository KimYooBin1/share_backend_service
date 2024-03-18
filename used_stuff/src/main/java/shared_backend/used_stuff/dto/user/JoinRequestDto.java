package shared_backend.used_stuff.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;

@Data
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
}
