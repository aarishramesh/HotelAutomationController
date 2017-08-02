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
public class AirConditioner extends ElectronicEquipment {

	private int acNumber;

	public AirConditioner(int powerRating, int acNumber, boolean switchedOn) {
		super(powerRating, switchedOn);
		this.acNumber = acNumber;
	}

	@Override
	public String toString() {
		return Corridor.DOUBLE_SPACES + Corridor.DOUBLE_SPACES + "AC : "
				+ (super.isSwitchedOn() ? "ON" : "OFF");
	}
}
