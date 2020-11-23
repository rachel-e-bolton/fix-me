package fix.router.exchange.messaging;

public final class Checksum {
	public static String calculateChecksum(String message) {
		Integer total = 0;
		Integer length = (message.indexOf("10=") > -1) ? message.length() - 7 : message.length();

		for (int i = 0; i < length; i++) {
			total += message.charAt(i);
		}
		System.out.println(String.format("%03d for %s", (total % 256), message));
		return String.format("%03d", (total % 256));
	}

	public static String appendCheckSum(String message) {
		String checksum = calculateChecksum(message);
		return message + "10=" + checksum + "|";
	}
}
