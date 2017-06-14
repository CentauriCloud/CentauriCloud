package org.centauri.cloud.cloud.template;

import java.io.File;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SharedFile {

	@Getter private final File file;
	@Getter private final String name;
	
}