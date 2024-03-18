package shared_backend.used_stuff.dto.board;

import java.time.LocalDateTime;

public interface BoardListDto {
	Long getId();
	String getWriter();
	String getTitle();
	LocalDateTime getCreateDate();
	Integer getLikes();
}
