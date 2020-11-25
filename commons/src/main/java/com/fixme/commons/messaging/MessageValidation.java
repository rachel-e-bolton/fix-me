package com.fixme.commons.messaging;

import java.util.Arrays;
import java.util.List;

public class MessageValidation {
	public static void validateChecksum(String rawString) throws Exception {
		
		List<String> parts = Arrays.asList(rawString.split("\\|"));
		String checksum = parts.get(parts.size() - 1).split("=")[1];

		if (!checksum.equals(MessageChecksum.calculateChecksum(rawString))) {
			throw new Exception("Checksum is invalid");
		}
	}

	public static void validateCodePosition(String rawString) throws Exception {

		Integer firstElement = 0;
		List<String> parts = Arrays.asList(rawString.split("\\|"));
		Integer lastElement = parts.size() - 1;

		if (!parts.get(firstElement).startsWith("35=")) {
			throw new Exception("MsgType tag is missing");
		}

		if (!parts.get( lastElement).startsWith("10=")) {
			throw new Exception("Checksum is missing");
		}
	}

	public static void validateMessageFormat(String rawString) throws Exception {

		List<String> parts = Arrays.asList(rawString.split("\\|"));
		for (String part : parts) {
			if (part.split("=").length < 2) {
				throw new Exception(String.format("Part of message is malformed at %s", part));
			}
		}
	}

	public static void validateDuplicateCodes(String rawString) throws Exception {
		List<String> parts = Arrays.asList(rawString.split("\\|"));

		for (String part : parts) {
			Integer count = 0;
			for (String check : parts) {
				count += (check.startsWith(part.split("=")[0] + "=")) ? 1 : 0;
			}
			if (count > 1) {
				throw new Exception(String.format("Duplicate code %s in message", part));
			}
		}
	}

	public static void validateMessage(String rawString) throws Exception {
		validateMessageFormat(rawString);
		validateCodePosition(rawString);
		validateDuplicateCodes(rawString);
		validateChecksum(rawString);
	}


}
