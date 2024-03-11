package shared_backend.used_stuff.entity.shopboard;

import static jakarta.persistence.EnumType.*;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.user.User;

@Entity
public class ShopBoard {
	@Id @GeneratedValue
	private Long id;

	private String title;
	private String content;
	private String url;
	private int price;
	@Enumerated(STRING)
	private Status status;
	@Enumerated(STRING)
	private ProductStatus productStatus;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private LocalDateTime soldDate;
	private int like;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
