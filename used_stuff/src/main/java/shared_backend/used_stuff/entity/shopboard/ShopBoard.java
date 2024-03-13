package shared_backend.used_stuff.entity.shopboard;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import shared_backend.used_stuff.base.BaseEntity;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.user.User;

@Entity
public class ShopBoard extends BaseEntity {
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
	private LocalDateTime soldDate;
	private int likes;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public ShopBoard() {
	}
}
