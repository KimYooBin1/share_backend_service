package shared_backend.used_stuff.dto;

import java.time.LocalDateTime;

import lombok.Data;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.shopboard.ProductStatus;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;

@Data
public class ShopBoardDetailResponse {
	private Long id;
	private String title;
	private String content;
	private String url;
	private int price;
	private Address address;
	private int like;
	private String sellerName;
	private ProductStatus status;
	private LocalDateTime createDate;

	public ShopBoardDetailResponse(ShopBoard shopBoard){
		this.id = shopBoard.getId();
		this.title = shopBoard.getTitle();
		this.content = shopBoard.getContent();
		this.url = shopBoard.getUrl();
		this.price = shopBoard.getPrice();
		this.address = shopBoard.getAddress();
		this.like = shopBoard.getLikes();
		this.sellerName = shopBoard.getUser().getProfile().getName();
		this.status = shopBoard.getProductStatus();
		this.createDate = shopBoard.getCreateDate();
	}
}
