/**
 * 
 */
package com.hotel.automation.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hotel.automation.criteria.PowerConsumptionCriteria;

import lombok.Data;

/**
 * Represents a passageway for a Floor.
 * 
 * @author aarishramesh
 *
 */
@Data
public abstract class Corridor {

	/**
	 * Added for intending the text in the Output.
	 */
	static final String DOUBLE_SPACES = "  ";

	private List<LightBulb> lightBulbs;

	private List<DecorativeLamp> decorativeLamps;

	private List<AirConditioner> airConditioners;

	/**
	 * Create a new Corridor with the default equipments.
	 */
	public Corridor(CorridorType type) {
		lightBulbs = new ArrayList<>();
		lightBulbs.add(new LightBulb(
				PowerConsumptionCriteria.LIGHTBULB_POWER_RATING, lightBulbs
				.size(), type == CorridorType.MAIN));
		decorativeLamps = new ArrayList<>();
		decorativeLamps.add(new DecorativeLamp(0, PowerConsumptionCriteria.DECORATIVELIGHTS_POWER_RATING,
				type == CorridorType.MAIN));
		if (type == CorridorType.MAIN) {
			decorativeLamps.add(new DecorativeLamp(1, PowerConsumptionCriteria.LIGHTBULB_POWER_RATING,
					type == CorridorType.MAIN));
		}
		
		airConditioners = new ArrayList<>();
		airConditioners.add(new AirConditioner(
				PowerConsumptionCriteria.AIRCONDITIONER_POWER_RATING,
				airConditioners.size(), true));
		setType(type);
	}

	public void addLightBulbs(LightBulb... lightBulbs) {
		this.lightBulbs.addAll(Arrays.asList(lightBulbs));
	}

	public void addDecorativeLamps(DecorativeLamp... decorativeLamps) {
		this.decorativeLamps.addAll(Arrays.asList(decorativeLamps));
	}

	public void addAirConditioners(AirConditioner... airConditioners) {
		this.airConditioners.addAll(Arrays.asList(airConditioners));
	}

	/**
	 * @return the type
	 */
	public abstract CorridorType getType();

	/**
	 * @param type
	 *            the type to set
	 */
	public abstract void setType(CorridorType type);

	static enum CorridorType {
		MAIN, SUB
	}

	public List<ElectronicEquipment> getAllElectronicEquipments() {
		List<ElectronicEquipment> entireEquipments = new ArrayList<ElectronicEquipment>();
		entireEquipments.addAll(lightBulbs);
		entireEquipments.addAll(airConditioners);
		entireEquipments.addAll(decorativeLamps);
		return entireEquipments;
	}
}
