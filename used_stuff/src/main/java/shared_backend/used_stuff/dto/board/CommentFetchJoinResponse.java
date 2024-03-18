package shared_backend.used_stuff.dto.board;

import java.time.LocalDateTime;

import lombok.Data;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;

@Data
public class CommentFetchJoinResponse {
	private Long c_id;
	private String writer;
	private String content;
	private LocalDateTime createDate;
	private Status status;

	public CommentFetchJoinResponse(BoardComment comment) {
		this.c_id = comment.getId();
		this.writer = comment.getWriter();
		this.content = comment.getContent();
		this.createDate = comment.getCreateDate();
		this.status = comment.getStatus();
	}
}
