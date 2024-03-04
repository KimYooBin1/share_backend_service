package shared_backend.used_stuff.entity.board;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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

	private LocalDateTime create_date;

	private LocalDateTime update_date;

}
