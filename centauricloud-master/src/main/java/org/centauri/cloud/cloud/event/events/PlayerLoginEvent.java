
package org.centauri.cloud.cloud.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.cloud.event.Event;
import org.centauri.cloud.cloud.player.Player;

@RequiredArgsConstructor
public class PlayerLoginEvent implements Event {

	@Getter private final Player player;

}
