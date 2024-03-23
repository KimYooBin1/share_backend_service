package shared_backend.used_stuff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.repository.custom.CommentRepositoryCustom;

public interface CommentRepository extends JpaRepository<BoardComment, Long>, CommentRepositoryCustom {
	public List<BoardComment> findAllByBoardAndStatusNot(Board board, Status status);
	@EntityGraph(attributePaths = {"board"})
	public Optional<BoardComment> findById(Long id);
}
