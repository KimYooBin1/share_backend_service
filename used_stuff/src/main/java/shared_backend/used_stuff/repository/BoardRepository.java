package shared_backend.used_stuff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.dto.board.BoardListDto;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.Status;

public interface BoardRepository extends JpaRepository<Board, Long> {
	Page<BoardListDto> findByTitleContainingAndStatusNot(String title, Status status,Pageable pageable);

	Page<BoardListDto> findByWriterContainingAndStatusNot(String writer, Status status, Pageable pageable);

	Page<BoardListDto> findAllByStatusNot(Status status, Pageable pageable);

	List<BoardListDto> findAllProjectedByLikesGreaterThan(int like, Pageable pageable);

	@EntityGraph(attributePaths = {"boardComments"})
	Optional<Board> findById(Long id);
}
