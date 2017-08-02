/**
 * 
 */
package com.hotel.automation.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author aarishramesh
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MainCorridor extends Corridor {

	private CorridorType type;

	private boolean nightTime;

	private int corridorNumber;

	public MainCorridor(int corridorNumber) {
		super(CorridorType.MAIN);
		this.corridorNumber = corridorNumber;
	}

	@Override
	public String toString() {
		return DOUBLE_SPACES + "Main corridor " + (corridorNumber + 1);
	}

}
