package shared_backend.used_stuff.dto.board;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;

@Data
public class CommentResponse {
	private Long c_id;
	private String writer;
	private String password;
	private String content;
	private Status status;
	private LocalDateTime createDate;

	public CommentResponse(BoardComment comment) {
		this.c_id = comment.getId();
		this.writer = comment.getWriter();
		this.password = comment.getPassword();
		this.content = comment.getContent();
		this.createDate = comment.getCreateDate();
		this.status = comment.getStatus();
	}

	@QueryProjection
	public CommentResponse(Long c_id, String writer, String password, String content, Status status,
		LocalDateTime createDate) {
		this.c_id = c_id;
		this.writer = writer;
		this.password = password;
		this.content = content;
		this.status = status;
		this.createDate = createDate;
	}
}
