package shared_backend.used_stuff.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateCommentRequest {
	@NotEmpty
	private String password;
	private String content;
}
