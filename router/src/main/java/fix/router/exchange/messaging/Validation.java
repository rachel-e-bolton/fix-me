package fix.router.exchange.messaging;

import java.util.List;

import fix.router.exchange.exceptions.ValidationException;

public final class Validation {
	public static void validateChecksum(List<String> rawParts) throws ValidationException {

		String rawMessage = String.join("|", rawParts) + "|";
		String checksum = rawParts.get(rawParts.size() - 1).split("=")[1];

		if (!checksum.equals(Checksum.calculateChecksum(rawMessage))) {
			throw new ValidationException("Checksum is invalid");
		}
	}

	public static void validateCodePosition(List<String> rawParts) throws ValidationException {

		Integer firstElement = 0;
		Integer lastElement = rawParts.size() - 1;

		if (!rawParts.get(firstElement).startsWith("35=")) {
			throw new ValidationException("Message must start with 35=MsgType");
		}

		if (!rawParts.get( lastElement).startsWith("10=")) {
			throw new ValidationException("Checksum 10= should always be at end of message");
		}
	}

	public static void validateMessageFormat(List<String> rawParts) throws ValidationException {
		for (String part : rawParts) {
			if (part.split("=").length < 2) {
				System.out.println(part.split("=").length);
				throw new ValidationException(String.format("Part of message is malformed at %s", part));
			}
		}
	}

	public static void validateDuplicateCodes(List<String> rawParts) throws ValidationException {
		for (String part : rawParts) {
			Integer count = 0;
			for (String check : rawParts) {
				count += (check.startsWith(part.split("=")[0] + "=")) ? 1 : 0;
			}
			if (count > 1) {
				throw new ValidationException(String.format("Duplicate code %s in message", part));
			}
		}
	}
}
