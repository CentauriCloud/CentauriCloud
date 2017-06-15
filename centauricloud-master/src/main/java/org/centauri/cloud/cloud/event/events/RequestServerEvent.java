package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.event.Event;
import org.centauri.cloud.cloud.template.Template;

@RequiredArgsConstructor
public class RequestServerEvent implements Event {
	
	@Getter private final Template template;

}