package shared_backend.used_stuff.dto.board;

import java.time.LocalDateTime;

import lombok.Data;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;

@Data
public class CommentResponse {
	private Long b_id;
	private Long c_id;
	private String writer;
	private String password;
	private String content;
	private Status status;
	private LocalDateTime createDate;

	public CommentResponse(Long boardId, BoardComment comment) {
		this.b_id = boardId;
		this.c_id = comment.getId();
		this.writer = comment.getWriter();
		this.password = comment.getPassword();
		this.content = comment.getContent();
		this.createDate = comment.getCreateDate();
		this.status = comment.getStatus();
	}
}
