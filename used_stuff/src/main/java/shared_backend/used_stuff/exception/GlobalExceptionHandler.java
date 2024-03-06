package shared_backend.used_stuff.exception;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared_backend.used_stuff.service.NotEqualPassword;

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

	@ExceptionHandler(NotEqualPassword.class)	//boards/{id} 에서 id 값에 long 값이 안들어오고 comments 와 같이 String 이 들어와도  비번틀렸다 에러
	public E IAE(){
		return new E("비밀번호가 올바르지 않습니다");}
}
