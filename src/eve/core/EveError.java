package eve.core;

public class EveError extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EveError() {
		super();
	}
	
	public EveError(String message) {
		super(message);
	}
}
