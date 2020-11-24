package com.fixme.commons.messaging;

import java.util.List;

public class MessageValidation {
	public static void validateChecksum(List<String> rawParts) throws Exception {

		String rawMessage = String.join("|", rawParts) + "|";
		String checksum = rawParts.get(rawParts.size() - 1).split("=")[1];

		if (!checksum.equals(MessageChecksum.calculateChecksum(rawMessage))) {
			throw new Exception("Checksum is invalid");
		}
	}

	public static void validateCodePosition(List<String> rawParts) throws Exception {

		Integer firstElement = 0;
		Integer lastElement = rawParts.size() - 1;

		if (!rawParts.get(firstElement).startsWith("35=")) {
			throw new Exception("MsgType tag is missing");
		}

		if (!rawParts.get( lastElement).startsWith("10=")) {
			throw new Exception("Checksum is missing");
		}
	}

	public static void validateMessageFormat(List<String> rawParts) throws Exception {
		for (String part : rawParts) {
			if (part.split("=").length < 2) {
				throw new Exception(String.format("Part of message is malformed at %s", part));
			}
		}
	}

	public static void validateDuplicateCodes(List<String> rawParts) throws Exception {
		for (String part : rawParts) {
			Integer count = 0;
			for (String check : rawParts) {
				count += (check.startsWith(part.split("=")[0] + "=")) ? 1 : 0;
			}
			if (count > 1) {
				throw new Exception(String.format("Duplicate code %s in message", part));
			}
		}
	}
}
