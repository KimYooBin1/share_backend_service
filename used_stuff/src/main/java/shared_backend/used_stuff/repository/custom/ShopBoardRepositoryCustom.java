package shared_backend.used_stuff.repository.custom;

import java.util.List;

import com.querydsl.core.Tuple;

import shared_backend.used_stuff.dto.shop.ShopBoardResponse;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;

public interface ShopBoardRepositoryCustom {
	List<ShopBoard> findOrderList(Long id);
	List<ShopBoard> findOrderListByName(String name);
}
