package shared_backend.used_stuff.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateBoardRequest {
	@NotEmpty
	private String writer;
	@NotEmpty
	@Length(min = 5, max = 10)
	private String password;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
}
