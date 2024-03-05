package shared_backend.used_stuff.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateBoardRequest {
	@NotEmpty
	private String password;
	private String title;
	private String content;
}
