package shared_backend.used_stuff.dto.shop;

import static lombok.AccessLevel.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shared_backend.used_stuff.entity.Address;

@Data
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ShopBoardRequest {
	@NotNull
	private String title;
	@NotNull
	private String content;
	@NotNull
	private String url;
	@NotNull
	private Integer price;
	@NotNull
	private Address address;

	public ShopBoardRequest(String title, String content, String url, int price, Address address) {
		this.title = title;
		this.content = content;
		this.url = url;
		this.price = price;
		this.address = address;
	}
}
