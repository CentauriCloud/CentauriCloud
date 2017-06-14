package org.centauri.cloud.cloud.template;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Template {
	
	@Getter private final String name;
	@Getter private final File dir;
	@Getter private Set<SharedFile> sharedFiles = new HashSet<>();
	
	public void compress() {
		
	}

}