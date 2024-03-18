package shared_backend.used_stuff.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;

@Data
public class UpdateUserRequest {
	@NotNull
	private String password;
	private String name;
	private Address address;
	private int age;
	private Gender gender;
}
