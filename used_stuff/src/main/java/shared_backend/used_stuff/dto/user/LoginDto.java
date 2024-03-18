package shared_backend.used_stuff.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDto {
	@NotNull
	private String username;
	@NotNull
	private String password;
}
