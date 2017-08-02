package com.hotel.automation.model;

import lombok.Data;

/**
 * Abstracts Electronic equipment in general for flexibility in design
 * 
 * @author aarishramesh
 *
 */
@Data
public abstract class ElectronicEquipment {
	
	private int powerRating;

	private boolean switchedOn;
	
	public ElectronicEquipment(int powerRating, boolean switchedOn) {
		this.powerRating = powerRating;
		this.switchedOn = switchedOn;
	}
}
