package shared_backend.used_stuff.entity.board;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import shared_backend.used_stuff.base.BaseEntity;

@Entity
@Getter
public class BoardComment extends BaseEntity {
	@Id @GeneratedValue
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	private String writer;

	private String password;

	private String content;

	@Enumerated(STRING)
	private Status status;

	public BoardComment() {
	}

	public BoardComment(String writer, String password, String content) {
		this.writer = writer;
		this.password = password;
		this.content = content;
		this.status = regist;
	}

	public void boardSet(Board board){
		this.board = board;
	}

	public void editComment(String content, Status status){
		this.content = content;
		this.status = status;
	}

	public void deleteComment(Status status) {
		this.status = status;
	}
}
