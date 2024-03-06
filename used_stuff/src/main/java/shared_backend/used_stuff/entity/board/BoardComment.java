package shared_backend.used_stuff.entity.board;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static java.time.LocalDateTime.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BoardComment {
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

	private LocalDateTime create_date;

	private LocalDateTime update_date;

	public BoardComment() {
	}

	public BoardComment(String writer, String password, String content) {
		this.writer = writer;
		this.password = password;
		this.content = content;
		this.create_date = now();
		this.update_date = now();
		this.status = regist;
	}
}