package shared_backend.used_stuff.entity.shopboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Comment {
	@Id @GeneratedValue
	private Long id;
}
