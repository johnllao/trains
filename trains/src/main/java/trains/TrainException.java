package trains;

/**
 * Logical error details encountered while processing the application
 */
public class TrainException extends Exception {
	private static final long serialVersionUID = 4879539657849661980L;
	
	public TrainException(String message) {
		super(message);
	}

}
