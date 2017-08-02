/**
 * 
 */
package com.hotel.automation.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hotel.automation.model.Floor;
import com.hotel.automation.model.Hotel;
import com.hotel.automation.model.SensorInput;
import com.hotel.automation.model.SubCorridor;


/**
 * @author aarishramesh
 *
 */
public class PowerControllerHelperTest {

	private PowerControllerHelper powerControllerHelper;

	private Hotel hotel;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		powerControllerHelper = new PowerControllerHelper();
		hotel = new Hotel.HotelBuilder("New", 1, 1, 2).build();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerControllerHelper#switchACOnAtSubCorridor(com.hotel.automation.model.SubCorridor)}
	 * .
	 */
	@Test
	public void testSwitchACOnAtSubCorridor() {
		SubCorridor subCorridor = hotel.getFloors().get(0).getSubCorridors()
				.get(0);
		powerControllerHelper.switchACOnAtSubCorridor(subCorridor);
		assertTrue("AC is Off!", subCorridor.getAirConditioners().get(0)
				.isSwitchedOn());
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerControllerHelper#switchACOffAtSubCorridor(com.hotel.automation.model.SubCorridor)}
	 * .
	 */
	@Test
	public void testSwitchACOffAtSubCorridor() {
		SubCorridor subCorridor = hotel.getFloors().get(0).getSubCorridors()
				.get(0);
		powerControllerHelper.switchACOffAtSubCorridor(subCorridor);
		assertFalse("AC is still On!", subCorridor.getAirConditioners().get(0)
				.isSwitchedOn());
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerControllerHelper#isLightBulbSwitchedOnAtSubCorridor(com.hotel.automation.model.SubCorridor)}
	 * .
	 */
	@Test
	public void testIsLightBulbSwitchedOnAtSubCorridor() {
		SubCorridor subCorridor = hotel.getFloors().get(0).getSubCorridors()
				.get(0);
		assertFalse("LightBulb is On!",
				powerControllerHelper
						.isLightBulbSwitchedOnAtSubCorridor(subCorridor));
		subCorridor.getLightBulbs().get(0).setSwitchedOn(true);
		assertTrue("LightBulb is Off!",
				powerControllerHelper
						.isLightBulbSwitchedOnAtSubCorridor(subCorridor));
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerControllerHelper#findMatchingFloor(com.hotel.automation.model.Hotel, com.hotel.automation.model.SensorInput)}
	 * .
	 */
	@Test
	public void testFindMatchingFloor() {
		SensorInput motion = new SensorInput(1, 1, false);
		Floor matchingFloor = powerControllerHelper.findMatchingFloor(hotel,
				motion);
		assertTrue("Not the same floor", 0 == matchingFloor.getFloorNumber());
		// Let's create a SensorInput in an invalid Floor.
		motion = new SensorInput(-1, 1, false);
		try {
			matchingFloor = powerControllerHelper.findMatchingFloor(hotel,
					motion);
			assertNull("Fake floor found!", matchingFloor);
		} catch (NoSuchElementException exception){
			// Expected. Ignored.
		}
			
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerControllerHelper#findMatchingSubCorridor(com.hotel.automation.model.SensorInput, com.hotel.automation.model.Floor)}
	 * .
	 */
	@Test
	public void testFindMatchingSubCorridor() {
		SensorInput motion = new SensorInput(1, 2, false);
		Floor matchingFloor = powerControllerHelper.findMatchingFloor(hotel,
				motion);
		SubCorridor matchingSubCorridor = powerControllerHelper
				.findMatchingSubCorridor(motion, matchingFloor);
		assertTrue("Not the same floor",
				1 == matchingSubCorridor.getCorridorNumber());
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerControllerHelper#findSomeOtherSubCorridor(com.hotel.automation.model.SensorInput, com.hotel.automation.model.Floor, com.hotel.automation.model.SubCorridor)}
	 * .
	 */
	@Test
	public void testFindSomeOtherSubCorridor() {
		SensorInput motion = new SensorInput(1, 2, false);
		Floor matchingFloor = powerControllerHelper.findMatchingFloor(hotel,
				motion);
		SubCorridor matchingSubCorridor = powerControllerHelper
				.findMatchingSubCorridor(motion, matchingFloor);
		SubCorridor someOtherSubCorridor = powerControllerHelper
				.findSomeOtherSubCorridor(motion, matchingFloor,
						matchingSubCorridor);
		assertFalse("It's the same SubCorridor",
				1 == someOtherSubCorridor.getCorridorNumber());
		// Let's remove the only other SubCorridor and see!
		SubCorridor removedSubCorridor = hotel.getFloors().get(0)
				.getSubCorridors().remove(0);
		someOtherSubCorridor = powerControllerHelper.findSomeOtherSubCorridor(
				motion, matchingFloor, matchingSubCorridor);
		assertTrue("It's the same SubCorridor",
				1 == someOtherSubCorridor.getCorridorNumber());
		// Let's add it back. Our test is successful. It has to be, otherwise it
		// can't be executing this line!
		hotel.getFloors().get(0).getSubCorridors().add(removedSubCorridor);
	}

}
