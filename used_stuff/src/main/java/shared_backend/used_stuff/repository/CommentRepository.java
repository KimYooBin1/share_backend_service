package shared_backend.used_stuff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {
	public List<BoardComment> findAllByBoardAndStatusNot(Board board, Status status);
	public Optional<BoardComment> findById(Long id);



}
