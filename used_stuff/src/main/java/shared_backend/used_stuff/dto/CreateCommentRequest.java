package shared_backend.used_stuff.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCommentRequest {
	@NotEmpty
	private String writer;
	@NotEmpty
	@Length(min = 5, max = 10)
	private String password;
	@NotEmpty
	private String content;

	public CreateCommentRequest(String writer, String password, String content) {
		this.writer = writer;
		this.password = password;
		this.content = content;
	}
}
