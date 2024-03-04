package shared_backend.used_stuff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
