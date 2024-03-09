package shared_backend.used_stuff.exception;

import java.util.NoSuchElementException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@Data
	@AllArgsConstructor
	static class E {
		private String error;
	}

	@ExceptionHandler(NoSuchElementException.class)
	public E NSE() {
		return new E("id값이 잘못되었습니다");
	}

	@ExceptionHandler(NotEqualPassword.class)
	//boards/{id} 에서 id 값에 long 값이 안들어오고 comments 와 같이 String 이 들어와도  비번틀렸다 에러
	public E NEP() {
		return new E("비밀번호가 올바르지 않습니다");
	}

	@ExceptionHandler(AlreadyDeletedException.class)
	public E ADE() {
		return new E("이미 삭제되어있습니다");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public E MANV(MethodArgumentNotValidException e) {
		//실제 valid 로 받은 detail error message 출력 방법 찾아보기
		return new E("argument error");
	}
	@ExceptionHandler(ExpiredJwtException.class)
	public E EJE() {
		return new E("login token is expired");
	}
}
