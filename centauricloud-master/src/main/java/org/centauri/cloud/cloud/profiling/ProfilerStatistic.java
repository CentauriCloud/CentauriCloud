package org.centauri.cloud.cloud.profiling;

import lombok.Data;

@Data
public class ProfilerStatistic {

	private long min = Long.MAX_VALUE, max;
	private double avg;

}