package shared_backend.used_stuff.service;

public class NotEqualPassword extends RuntimeException{
	public NotEqualPassword() {
		super();
	}

	public NotEqualPassword(String message) {
		super(message);
	}

	public NotEqualPassword(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEqualPassword(Throwable cause) {
		super(cause);
	}

	protected NotEqualPassword(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
