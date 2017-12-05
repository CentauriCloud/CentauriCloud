package org.centauri.cloud.cloud.profiling;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CentauriProfiler {

	@Getter private static CentauriProfiler instance = new CentauriProfiler();
	@Getter private final List<Profile> profiles = Collections.synchronizedList(new ArrayList<Profile>());
	@Getter @Setter private boolean enabled = true; //Needed for profiling start-time

	public Profile start(String key) {
		if (!this.isEnabled())
			return null;
		return new Profile(key, System.currentTimeMillis());
	}

	public void stop(Profile profile) {
		if (profile == null)
			return;
		profile.time = System.currentTimeMillis() - profile.getTime();
		profiles.add(profile);
	}

	public void checkEnabled() {
		this.enabled = Boolean.valueOf(PropertyManager.getInstance().getProperties().getProperty("profiling", "false"));
		Cloud.getLogger().info("Profiler {}!", this.isEnabled() ? "enabled" : "disabled");
		if (!this.isEnabled())
			profiles.clear();
	}

	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Profile {

		@Getter private final String key;
		@Getter private long time; //overwrite on stop() method to difference

	}

}