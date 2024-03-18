package shared_backend.used_stuff.entity.shopboard;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static java.time.LocalDateTime.*;
import static lombok.AccessLevel.*;
import static shared_backend.used_stuff.entity.shopboard.ProductStatus.*;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.base.BaseEntity;
import shared_backend.used_stuff.dto.shop.ShopBoardRequest;
import shared_backend.used_stuff.dto.shop.UpdateShopBoardRequest;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.user.User;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id"})
@Getter
@Slf4j
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "shopBoard-with-user",
		attributeNodes = {
			@NamedAttributeNode("user"),
		}
	),
	@NamedEntityGraph(
		name = "shopBoard-with-user-and-profile",
		attributeNodes = {
			@NamedAttributeNode(value = "user", subgraph = "profile"),
		},
		subgraphs = {@NamedSubgraph(
			name = "profile",
			attributeNodes = {
				@NamedAttributeNode("profile")
			}
		)}
	)
})
public class ShopBoard extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String content;
	private String url;
	private int price;
	@Embedded
	private Address address;

	@Enumerated(STRING)
	private Status status;
	@Enumerated(STRING)
	private ProductStatus productStatus;
	private LocalDateTime soldDate;
	private int likes;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "buyer_id")
	private User buyer;

	public ShopBoard(ShopBoardRequest request, User user) {
		this.title = request.getTitle();
		this.content = request.getContent();
		this.url = request.getUrl();
		this.price = request.getPrice();
		this.address = request.getAddress();
		this.status = Status.regist;
		this.productStatus = sell;
		this.soldDate = null;
		this.likes = 0;
		this.user = user;
		user.getBoards().add(this);
	}

	public void updateShopBoard(UpdateShopBoardRequest request) {
		if(request.getTitle() != null){
			this.title = request.getTitle();
		}
		if(request.getContent() != null){
			this.content = request.getContent();
		}
		if(request.getUrl() != null){
			this.url = request.getUrl();
		}
		if(request.getPrice() != 0){
			this.price = request.getPrice();
		}
		if(request.getAddress() != null){
			this.address = request.getAddress();
		}
	}

	public void statusChange(Status status){
		this.status = status;
	}

	public void purchase(User user) {
		this.buyer = user;
		this.soldDate = now();
		this.productStatus = sold;
		user.changePoint(-this.price);
		this.user.changePoint(this.price);
		user.getOrderBoards().add(this);
	}

	public void cancel(User user) {
		this.buyer = null;
		this.soldDate = null;
		user.changePoint(this.price);
		this.user.changePoint(-this.price);
		this.productStatus = sell;
	}

	public void testUserChange(User user) {
		this.user = user;
	}

	public void testBuyerChange(User user) {
		this.buyer = user;
	}
}
