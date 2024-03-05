package shared_backend.used_stuff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {
	public List<BoardComment> findAllByBoard(Board board);
}
