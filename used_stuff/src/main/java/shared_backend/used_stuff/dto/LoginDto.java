package shared_backend.used_stuff.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDto {
	private String username;
	private String password;
}
