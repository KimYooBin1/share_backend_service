package shared_backend.used_stuff.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
	// TODO : exception 추가하기
	INVALID_PARAMS(400,"InvalidParams","필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다."),
	NOT_FOUND(404, "NotFound", "존재하지 않는 데이터입니다."),
	UNAUTORIZED(401, "Unauthorized", "토큰 정보가 유효하지 않습니다."),
	UNAVAILABLE(401, "Unavailable", "회원가입이 완료되지 않은 사용자입니다.");

	private final int status;
	private final String code;
	private final String error;

}
