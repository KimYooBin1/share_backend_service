package shared_backend.used_stuff.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Comment {
	@Id @GeneratedValue
	private Long id;
}
