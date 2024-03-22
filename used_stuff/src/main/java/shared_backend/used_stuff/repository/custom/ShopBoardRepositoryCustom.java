package shared_backend.used_stuff.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shared_backend.used_stuff.entity.shopboard.ShopBoard;

public interface ShopBoardRepositoryCustom {
	Page<ShopBoard> findOrderSearchList(String name, String type, String search, Pageable pageable);
}
