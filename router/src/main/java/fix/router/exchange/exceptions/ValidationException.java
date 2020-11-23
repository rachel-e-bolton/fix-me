package fix.router.exchange.exceptions;

import java.util.logging.Logger;

public class ValidationException extends Exception {
	/**
	 * Validation Exception
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger("Router");
	public ValidationException(String message) {
		super(message);
		LOGGER.warning("ValidationException: " + message);
	}
}
