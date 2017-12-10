package org.centauri.cloud.common.network.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ObjectWrapper <T> {

	@Getter @Setter private T value;

}