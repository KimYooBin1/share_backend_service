package shared_backend.used_stuff.entity.pointboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PointBoard {
	@Id @GeneratedValue
	private Long id;
}
