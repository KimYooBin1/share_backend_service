package shared_backend.used_stuff.dto.oauth2;

import lombok.Data;

@Data
public class UserDTO {
	private String role;
	private String name;
	private String username;
}
