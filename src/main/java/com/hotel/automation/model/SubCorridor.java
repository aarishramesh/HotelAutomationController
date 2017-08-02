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
public class SubCorridor extends Corridor {

	private CorridorType type;

	private boolean motionDetected;

	private int corridorNumber;

	public SubCorridor(int corridorNumber) {
		super(CorridorType.SUB);
		this.corridorNumber = corridorNumber;
	}

	@Override
	public String toString() {
		return DOUBLE_SPACES + "Sub corridor " + (corridorNumber + 1);
	}

}
