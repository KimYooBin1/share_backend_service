package shared_backend.used_stuff.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
	@NotNull
	private String checkPassword;
	@NotNull
	private String editPassword;
}
