/**
 * 
 */
package com.hotel.automation.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.hotel.automation.model.Floor;
import com.hotel.automation.model.Hotel;
import com.hotel.automation.model.LightBulb;
import com.hotel.automation.model.SensorInput;
import com.hotel.automation.model.SubCorridor;

/**
 * Helps {@link PowerController} by sharing some of the tasks associated with
 * adjusting the Power Consumption level to the accepted Criteria.
 * 
 * @author aarishramesh
 *
 */
public class PowerControllerHelper {

	private static final PowerControllerHelper INSTANCE = new PowerControllerHelper();
			
	public static PowerControllerHelper getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Switches the AC On at a given SubCorridor.
	 * 
	 * @param subCorridor
	 */
	public void switchACOnAtSubCorridor(SubCorridor subCorridor) {
		subCorridor.getAirConditioners().get(0).setSwitchedOn(true);
	}

	/**
	 * Switches the AC Off at a given SubCorridor.
	 * 
	 * @param subCorridor
	 */
	public void switchACOffAtSubCorridor(SubCorridor subCorridor) {
		subCorridor.getAirConditioners().get(0).setSwitchedOn(false);
	}

	/**
	 * Checks if the {@link LightBulb} located at the given SubCorridor is
	 * Switched on.
	 * 
	 * @param subCorridor
	 * @return Switched on or not.
	 */
	public boolean isLightBulbSwitchedOnAtSubCorridor(SubCorridor subCorridor) {
		return subCorridor.getLightBulbs().get(0).isSwitchedOn();
	}

	/**
	 * For a given Hotel instance, it returns the corresponding Floor instance
	 * where the motion is detected.
	 * 
	 * @param hotel
	 * @param motion
	 * @return
	 */
	public Floor findMatchingFloor(Hotel hotel, SensorInput motion) {
		Optional<Floor> matchingFloorOptional = hotel
				.getFloors()
				.stream()
				.filter(floor -> floor.getFloorNumber() == (motion
						.getFloorNumber() - 1)).findFirst();

		return matchingFloorOptional.get();
	}

	/**
	 * In a given Floor, it returns the SubCorridor where the Motion is
	 * detected.
	 * 
	 * @param motion
	 * @param matchingFloor
	 * @return
	 */
	public SubCorridor findMatchingSubCorridor(SensorInput motion,
			Floor matchingFloor) {
		Optional<SubCorridor> matchingSubCorridorOptional = matchingFloor
				.getSubCorridors()
				.stream()
				.filter(subCorridor -> subCorridor.getCorridorNumber() == (motion
						.getSubCorridorNumber() - 1)).findFirst();

		return matchingSubCorridorOptional.get();
	}

	/**
	 * This tries to do the exact opposite of
	 * {@link #findMatchingSubCorridor(SensorInput, Floor)} method to retrieve some
	 * other SubCorridor in the same Floor. If there are no other SubCorridors,
	 * returns the only SubCorridor in that floor.
	 * 
	 * @param motion
	 * @param matchingFloor
	 * @param matchingSubCorridor
	 * @return
	 */
	public SubCorridor findSomeOtherSubCorridor(SensorInput motion,
			Floor matchingFloor, SubCorridor matchingSubCorridor) {
		Optional<SubCorridor> otherSubCorridorOptional = matchingFloor
				.getSubCorridors()
				.stream()
				.filter(subCorridor -> subCorridor.getCorridorNumber() != (motion
						.getSubCorridorNumber() - 1)).findFirst();
		SubCorridor someOtherSubCorridor;
		try {
			someOtherSubCorridor = otherSubCorridorOptional.get();
		} catch (NoSuchElementException exception) {
			// Well, if there's no Some other subCorridor, there's no other
			// option than to turn off the AC in the same SubCorridor!
			someOtherSubCorridor = matchingSubCorridor;
		}
		return someOtherSubCorridor;
	}

}
