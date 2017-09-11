/**
 * 
 */
package com.hotel.automation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import com.hotel.automation.criteria.PowerConsumptionCriteria;
import com.hotel.automation.model.Floor;
import com.hotel.automation.model.Hotel;
import com.hotel.automation.model.LightBulb;
import com.hotel.automation.model.SensorInput;
import com.hotel.automation.model.SubCorridor;

import lombok.Data;


/**
 * 
 * The Heartbeat of this Application - Observes the {@link SensorInput} events
 * generated by the Sensors, prints the Hotel state on every external input,
 * adjusts the state of the Floors based on the {@link PowerConsumptionCriteria}
 * 
 * @author aarishramesh
 *
 */
@Data
public final class PowerController implements Observer {

	private Hotel hotel;
	private PowerControllerHelper powerControllerHelper;

	/**
	 * Helps storing information on which SubCorridor's AC was turned on when
	 * some other Subcorridor's Light Bulb had to be switched ON to maintain the
	 * correct level of Power consumption.
	 */
	private Map<SubCorridor, SubCorridor> subCorridorCoordination;

	/**
	 * 
	 */
	public PowerController(Hotel hotel) {
		this.hotel = hotel;
		subCorridorCoordination = new ConcurrentHashMap<SubCorridor, SubCorridor>();
		powerControllerHelper = PowerControllerHelper.getInstance();
	}

	private boolean isPowerConsumptionExceededForFloor(Floor floor) {
		PowerConsumptionCriteria powerConsumptionCriteria = new PowerConsumptionCriteria();
		return !powerConsumptionCriteria.criteriaMetFor(floor);
	}

	@Override
	public void update(Observable o, Object arg) {
		SensorInput motion = (SensorInput) o;
		toggleSubCorridorsLights(motion, motion.isMotionDetected());
	}

	/**
	 * Toggles the {@link LightBulb} for the matching {@link SubCorridor} where
	 * the motion was detected (or not detected after certain time period).
	 * 
	 * Synchronized block is used for thread-safety/atomicity of {@link SensorInput} processing
	 * 
	 * @param SensorInput
	 *            The SensorInput instance.
	 * @param on
	 *            To turn on or not.
	 */
	private void toggleSubCorridorsLights(SensorInput motion, boolean on) {
		Floor matchingFloor = powerControllerHelper.findMatchingFloor(hotel,
				motion);
		SubCorridor matchingSubCorridor = powerControllerHelper
				.findMatchingSubCorridor(motion, matchingFloor);
		if (powerControllerHelper
				.isLightBulbSwitchedOnAtSubCorridor(matchingSubCorridor) == on) {
			// Nothing to do. Everything is as expected.
			System.out.println(hotel);
			return;

		}

		synchronized(this) {
			matchingSubCorridor.getLightBulbs().forEach(
					lightBulb -> lightBulb.setSwitchedOn(on));

			// If the power consumption exceeds, find some other subCorridor and
			// turn off its AC.
			if (isPowerConsumptionExceededForFloor(matchingFloor)) {
				SubCorridor someOtherSubCorridor = powerControllerHelper
						.findSomeOtherSubCorridor(motion, matchingFloor,
								matchingSubCorridor);
				powerControllerHelper
				.switchACOffAtSubCorridor(someOtherSubCorridor);
				subCorridorCoordination.put(matchingSubCorridor,
						someOtherSubCorridor);
			} else {
				// Else, we could turn the AC back on, which was turned off in the
				// previous iteration.
				SubCorridor otherSubCorridor = subCorridorCoordination
						.get(matchingSubCorridor);
				if (otherSubCorridor != null) {
					powerControllerHelper.switchACOnAtSubCorridor(otherSubCorridor);
					if(isPowerConsumptionExceededForFloor(matchingFloor)) {
						powerControllerHelper.switchACOffAtSubCorridor(otherSubCorridor);
					}
				}
			}
			System.out.println(hotel);
		}
	}
}
