package shared_backend.used_stuff.repository.custom;

import static shared_backend.used_stuff.entity.shopboard.QShopBoard.*;
import static shared_backend.used_stuff.entity.user.QPassword.*;
import static shared_backend.used_stuff.entity.user.QProfile.*;
import static shared_backend.used_stuff.entity.user.QUser.*;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.QProfile;
import shared_backend.used_stuff.entity.user.QUser;

@RequiredArgsConstructor
public class ShopBoardRepositoryImpl implements ShopBoardRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public List<ShopBoard> findOrderList(Long id) {
		QUser buyer = new QUser("buyer");
		QProfile buyerProfile = new QProfile("buyerProfile");
		return query
			.select(shopBoard)
			.from(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(shopBoard.user.profile, profile).fetchJoin()
			.join(shopBoard.buyer, buyer).fetchJoin()
			.join(buyer.profile, buyerProfile).fetchJoin()
			.join(shopBoard.buyer.profile, buyerProfile).fetchJoin()

			.where(shopBoard.status.eq(Status.delete).not(),
				shopBoard.buyer.id.eq(id))
			.fetch();
	}

	@Override
	public List<ShopBoard> findOrderListByName(String name) {
		QUser buyer = new QUser("buyer");
		QProfile buyerProfile = new QProfile("buyerProfile");
		return query
			.select(shopBoard)
			.from(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(shopBoard.buyer, buyer).fetchJoin()
			.join(buyer.password, password).fetchJoin()
			.join(user.profile, profile).fetchJoin()
			.join(buyer.profile, buyerProfile).fetchJoin()

			.where(shopBoard.status.eq(Status.delete).not(),
				password.username.eq(name))
			.fetch();
	}
}
