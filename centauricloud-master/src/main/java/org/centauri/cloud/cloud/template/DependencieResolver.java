package org.centauri.cloud.cloud.template;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import lombok.SneakyThrows;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DependencieResolver {
	
	@SneakyThrows
	public static void resolveDependencies(Template template) {
		JSONObject config = (JSONObject) new JSONParser().parse(new FileReader(template.getDependenciesFile()));
	
		JSONArray dependencies = (JSONArray) config.get("dependencies");
	
		for(int i = 0; i < dependencies.size(); i++) {
			JSONObject json = (JSONObject) dependencies.get(i);
		
			Iterator<String> keys = json.keySet().iterator();

			while (keys.hasNext()) {
				String key = keys.next();
				System.out.println("Key :" + key + "  Value :" + json.get(key));
				template.getDependencies().put(new File(PropertyManager.getInstance().getProperties().getProperty("sharedDir") + key),
						new File(template.getDir().getPath() + "/" + json.get(key)));
			}
		}
	}

}