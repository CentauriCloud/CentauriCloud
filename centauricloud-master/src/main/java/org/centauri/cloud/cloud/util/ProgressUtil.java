package org.centauri.cloud.cloud.util;

public final class ProgressUtil {

	private ProgressUtil() {
	}

	private static final String PROGRESS_TEMPLATE = "|                                                  | 0%";
	private static final int SPACERS = 50;

	public static void printProgress(int max, int now) {
		String pattern = PROGRESS_TEMPLATE;
		int spacerReplaces = now * SPACERS / max;

		for (int i = 0; i < spacerReplaces; i++) {
			pattern = pattern.replaceFirst(" ", "=");
		}

		pattern = pattern.replace("0", Integer.toString(now * 100 / max));
		System.out.print("\r" + pattern);
	}
}