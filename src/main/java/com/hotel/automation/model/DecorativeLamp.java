package com.hotel.automation.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DecorativeLamp extends ElectronicEquipment {

	private int lightNumber;
	
	public DecorativeLamp(int lightNumber, int powerRating, boolean switchedOn) {
		super(powerRating, switchedOn);
		this.lightNumber = lightNumber;
	}

	@Override
	public String toString() {
		return Corridor.DOUBLE_SPACES + Corridor.DOUBLE_SPACES + "DecorativeLamp "
				+ (lightNumber + 1) + " : " + (super.isSwitchedOn() ? "ON" : "OFF");
	}
}
