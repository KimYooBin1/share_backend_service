package shared_backend.used_stuff.repository.custom;

import java.util.List;

import shared_backend.used_stuff.entity.shopboard.ShopBoard;

public interface ShopBoardRepositoryCustom {
	List<ShopBoard> findOrderSearchList(String name, String type, String search);
}
