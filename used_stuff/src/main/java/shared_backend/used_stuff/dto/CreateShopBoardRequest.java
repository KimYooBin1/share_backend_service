package shared_backend.used_stuff.dto;

import lombok.Data;
import shared_backend.used_stuff.entity.Address;

@Data
public class CreateShopBoardRequest {
	private String title;
	private String content;
	private String url;
	private int price;
	private Address address;
}
