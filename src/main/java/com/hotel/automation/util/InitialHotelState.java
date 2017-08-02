/**
 * 
 */
package com.hotel.automation.util;

import lombok.Data;

/**
 * Model class which abstracts initial hotel state
 * 
 * @author aarishramesh
 *
 */
@Data
public class InitialHotelState {

	private int floorCount;

	private int mainCorridorCount;

	private int subCorridorCount;

	public InitialHotelState(int floorCount, int mainCorridorCount,
			int subCorridorCount) {
		this.floorCount = floorCount;
		this.mainCorridorCount = mainCorridorCount;
		this.subCorridorCount = subCorridorCount;
	}

}
