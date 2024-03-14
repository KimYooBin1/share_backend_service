package shared_backend.used_stuff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.Status;

public interface BoardRepository extends JpaRepository<Board, Long> {
	Page<Board> findByTitleContainingAndStatusNot(String title, Status status,Pageable pageable);

	Page<Board> findByWriterContainingAndStatusNot(String writer, Status status, Pageable pageable);

	Page<Board> findAllByStatusNot(Status status, Pageable pageable);
}
