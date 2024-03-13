package shared_backend.used_stuff.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.dto.BoardResponse;
import shared_backend.used_stuff.entity.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
