package shared_backend.used_stuff.entity.shopboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ShopBoard {
	@Id @GeneratedValue
	private Long id;
}
