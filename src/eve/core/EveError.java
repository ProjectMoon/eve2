package eve.core;

public class EveError extends RuntimeException {
	public EveError() {
		super();
	}
	
	public EveError(String message) {
		super(message);
	}
}
