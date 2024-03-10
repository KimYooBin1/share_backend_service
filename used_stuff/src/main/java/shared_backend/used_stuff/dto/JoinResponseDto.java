package shared_backend.used_stuff.dto;

import lombok.Data;

@Data
public class JoinResponseDto {
	private String username;
	private String password;

	public JoinResponseDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
