package shared_backend.used_stuff.repository.custom;

import static shared_backend.used_stuff.entity.shopboard.QShopBoard.*;
import static shared_backend.used_stuff.entity.user.QPassword.*;
import static shared_backend.used_stuff.entity.user.QProfile.*;
import static shared_backend.used_stuff.entity.user.QUser.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.QPassword;
import shared_backend.used_stuff.entity.user.QProfile;
import shared_backend.used_stuff.entity.user.QUser;

@RequiredArgsConstructor
public class ShopBoardRepositoryImpl implements ShopBoardRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public List<ShopBoard> findOrderSearchList(String name, String type, String search) {
		QUser buyer = new QUser("buyer");
		QProfile buyerProfile = new QProfile("buyerProfile");
		QPassword buyerPassword = new QPassword("buyerPassword");

		BooleanExpression searchPredicate = null;

		if (search != null && !search.isEmpty()) {
			searchPredicate = shopBoard.title.contains(search);
		}

		JPAQuery<ShopBoard> findQuery = query
			.select(shopBoard)
			.from(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(user.password, password).fetchJoin()
			.join(user.profile, profile).fetchJoin()
			.leftJoin(shopBoard.buyer, buyer).fetchJoin()
			.leftJoin(buyer.password, buyerPassword).fetchJoin()
			.leftJoin(buyer.profile, buyerProfile).fetchJoin();

		if("sell".equals(type)){
			return findQuery
				.where(shopBoard.status.eq(Status.delete).not(),
					searchPredicate,
					password.username.eq(name))
				.fetch();
		}
		else if("buy".equals(type)){
			return findQuery
				.where(shopBoard.status.eq(Status.delete).not(),
					searchPredicate,
					buyerPassword.username.eq(name))
				.fetch();
		}
		else if(type == null){
			return findQuery.where(shopBoard.status.eq(Status.delete).not(),
					searchPredicate,
					(buyerPassword.username.eq(name).or(password.username.eq(name))))
				.fetch();
		}
		else {
			throw new IllegalArgumentException("Invalid type: " + type);
		}
	}
}
