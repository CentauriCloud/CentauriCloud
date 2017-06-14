package org.centauri.cloud.cloud.loadbalancing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.config.PropertyManager;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.cloud.template.Template;

public class ServerLoadBalancer extends TimerTask {

	public ServerLoadBalancer() {
		new Timer("ServerLoadBalancer").scheduleAtFixedRate(this, 1000L, Integer.valueOf(PropertyManager.getInstance().getProperties().getProperty("loadbalancerIntervall", "30")) * 1000L);
	}

	@Override
	public void run() {
		Map<String, Set<SpigotServer>> prefixToServers = new HashMap<>();
		Cloud.getInstance().getServerManager().getChannelToServer().values().forEach(server -> {
			if(!(server instanceof SpigotServer))
				return;
			
			if(!prefixToServers.containsKey(server.getPrefix()))
				prefixToServers.put(server.getPrefix(), new HashSet<>());
			
			prefixToServers.get(server.getPrefix()).add((SpigotServer) server);
		});
		
		Cloud.getInstance().getTemplateManager().getTemplates().forEach(template -> {
			Set<SpigotServer> freeServers = new HashSet<>();
			
			Set<SpigotServer> servers = prefixToServers.get(template.getName());
			if(servers == null) {
				ServerLoadBalancer.this.requestServer(template);
				return;
			}
			
			servers.forEach(server -> {
				if(server.getPlayers() < template.getMaxPlayers())
					freeServers.add(server);
			});
			
			if(freeServers.size() < template.getMinServersFree()) {
				ServerLoadBalancer.this.requestServer(template);
			}
		});
		
	}
	
	public void requestServer(Template template) {
		Cloud.getLogger().info("Request server for template {}!", template.getName());
	}
	
}