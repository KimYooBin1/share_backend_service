package shared_backend.used_stuff.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Board {
	@Id @GeneratedValue
	private Long id;
}
