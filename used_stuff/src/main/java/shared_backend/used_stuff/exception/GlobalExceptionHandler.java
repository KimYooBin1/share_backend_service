package shared_backend.used_stuff.exception;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;


@RestControllerAdvice
public class GlobalExceptionHandler {
	@Data
	@AllArgsConstructor
	static class E{
		private String error;
	}

	@ExceptionHandler(NoSuchElementException.class)
	public E NSE() {
		return new E("id값이 잘못되었습니다");
	}
}
