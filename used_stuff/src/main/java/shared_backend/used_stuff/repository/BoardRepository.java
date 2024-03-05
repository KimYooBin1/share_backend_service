package shared_backend.used_stuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shared_backend.used_stuff.entity.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
