package shared_backend.used_stuff.repository.custom;

import static shared_backend.used_stuff.entity.user.QPassword.*;
import static shared_backend.used_stuff.entity.user.QProfile.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.user.QUser;
import shared_backend.used_stuff.entity.user.User;

@RequiredArgsConstructor
public class PasswordRepositoryImpl implements PasswordRepositoryCustom {
	private final JPAQueryFactory query;
	@Override
	public Optional<User> findByUsernameFetchProfile(String name) {
		User user = query
			.selectFrom(QUser.user)
			.join(QUser.user.profile, profile).fetchJoin()
			.join(QUser.user.password, password).fetchJoin()
			.where(password.username.eq(name))
			.fetchOne();

		return Optional.ofNullable(user);
	}
}
