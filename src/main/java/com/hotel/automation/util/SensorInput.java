/**
 * 
 */
package com.hotel.automation.util;

/**
 * This is representative of an Intermediate state of a {@link Hotel} based on
 * the Sensor inputs keyed into the application. Currently, it keeps of
 * information like at which {@link Floor} and {@link SubCorridor} to turn the
 * {@link LightBulb} on/off.
 * 
 * @author asgs
 *
 */
public class SensorInput {

	private int floorNumber;

	private int subCorridorNumber;

	private boolean lightBulbToTurnOn;

	/**
	 * Default constructor.
	 */
	public SensorInput(int floorNumber, int subCorridorNumber,
			boolean lightBulbToTurnOn) {
		this.floorNumber = floorNumber;
		this.subCorridorNumber = subCorridorNumber;
		this.lightBulbToTurnOn = lightBulbToTurnOn;
	}

	/**
	 * @return the floorNumber
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

	/**
	 * @return the subCorridorNumber
	 */
	public int getSubCorridorNumber() {
		return subCorridorNumber;
	}

	/**
	 * @return the lightBulbToTurnOn
	 */
	public boolean isLightBulbToTurnOn() {
		return lightBulbToTurnOn;
	}

}
