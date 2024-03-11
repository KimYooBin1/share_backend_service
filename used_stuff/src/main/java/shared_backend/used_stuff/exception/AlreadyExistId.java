package shared_backend.used_stuff.exception;

public class AlreadyExistId extends RuntimeException{
	public AlreadyExistId() {
		super();
	}

	public AlreadyExistId(String message) {
		super(message);
	}

	public AlreadyExistId(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistId(Throwable cause) {
		super(cause);
	}
}
