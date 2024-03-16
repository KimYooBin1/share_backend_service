package shared_backend.used_stuff.dto;

import static lombok.AccessLevel.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shared_backend.used_stuff.entity.Address;

@Data
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UpdateShopBoardRequest {
	private String title;
	private String content;
	private String url;
	private int price;
	private Address address;

	public UpdateShopBoardRequest(String title, String content, String url, int price, Address address) {
		this.title = title;
		this.content = content;
		this.url = url;
		this.price = price;
		this.address = address;
	}
}
