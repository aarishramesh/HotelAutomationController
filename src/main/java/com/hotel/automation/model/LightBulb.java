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
public class LightBulb extends ElectronicEquipment {

	private int lightNumber;
	
	public LightBulb(int powerRating, int lightNumber, boolean switchedOn) {
		super(powerRating, switchedOn);
		this.lightNumber = lightNumber;
	}
	
	@Override
	public String toString() {
		return Corridor.DOUBLE_SPACES + Corridor.DOUBLE_SPACES + "Light "
				+ (lightNumber + 1) + " : " + (super.isSwitchedOn() ? "ON" : "OFF");
	}

}
