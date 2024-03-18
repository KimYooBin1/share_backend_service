package shared_backend.used_stuff.dto.shop;

import java.time.LocalDateTime;

import lombok.Data;
import shared_backend.used_stuff.entity.shopboard.ProductStatus;

@Data
public class ShopBoardResponse {
	private Long id;
	private String title;
	private String sellerName;
	private String buyerName;
	private ProductStatus status;
	private LocalDateTime createDate;

	//sold된 삼품 검색
	public ShopBoardResponse(Long id, String title, String sellerName, String buyerName, ProductStatus status,
		LocalDateTime createDate) {
		this.id = id;
		this.title = title;
		this.sellerName = sellerName;
		this.buyerName = buyerName;
		this.status = status;
		this.createDate = createDate;
	}

	//일반적인 검색
	public ShopBoardResponse(Long id, String title, String sellerName, ProductStatus status, LocalDateTime createDate) {
		this.id = id;
		this.title = title;
		this.sellerName = sellerName;
		this.status = status;
		this.createDate = createDate;
	}
}
