package org.centauri.cloud.cloud.util;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Level;
import org.centauri.cloud.cloud.Cloud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@AllArgsConstructor
public class LoggerOutputStream extends ByteArrayOutputStream {
	private final Level level;

	@Override
	public void flush() throws IOException {
		synchronized (this) {
			super.flush();
			String record = this.toString();
			super.reset();

			if (!record.trim().isEmpty()) {
				Cloud.getLogger().log(level, record);
			}
		}
	}
}
