package org.centauri.cloud.cloud.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.profiling.CentauriProfiler;

public class EventManager {
	
	private final Map<Class<? extends Event>, Set<EventHandler>> eventHandlers = new HashMap<>();

	public void registerEventHandler(Object obj) {
		for(Method method : obj.getClass().getDeclaredMethods()) {
			if(method.getGenericParameterTypes().length == 1 && method.getAnnotation(Listener.class) != null) {
				Class parameterClazz = method.getParameters()[0].getType();
				if(parameterClazz.getInterfaces()[0].equals(Event.class)) {
					if(!this.eventHandlers.containsKey(parameterClazz)) {
						this.eventHandlers.put(parameterClazz, new HashSet<>());
					}
					this.eventHandlers.get(parameterClazz).add(new EventHandler(obj, method));
				}
			}
		}
	}
	
	public void callEvent(Event event) {
		Set<EventHandler> eventSet = this.eventHandlers.get(event.getClass());
		
		if(eventSet == null) return;
		
		for (EventHandler eventHandler : eventSet) {
			CentauriProfiler.Profile profile = Cloud.getInstance().getProfiler().start("EventManager_callEvent_" + eventHandler.getInstance().getClass().getSimpleName());
			
			try {
				eventHandler.method.invoke(eventHandler.instance, event);
			} catch (Exception ex) {
				System.err.println("Something went wrong on during event call: ");
				Cloud.getLogger().error(ex.getMessage(), ex);
			}
			
			Cloud.getInstance().getProfiler().stop(profile);
		}
	}
	
	@RequiredArgsConstructor
	@Getter
	private static class EventHandler {
		private final Object instance;
		private final Method method;
	}
}
