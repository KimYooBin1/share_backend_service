package shared_backend.used_stuff.exception;

public class AlreadyDeletedException extends IllegalArgumentException{
	public AlreadyDeletedException() {
		super();
	}

	public AlreadyDeletedException(String s) {
		super(s);
	}

	public AlreadyDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyDeletedException(Throwable cause) {
		super(cause);
	}
}
