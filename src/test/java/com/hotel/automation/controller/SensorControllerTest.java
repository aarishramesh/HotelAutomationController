/**
 * 
 */
package com.hotel.automation.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hotel.automation.model.Hotel;
import com.hotel.automation.model.SensorInput;

/**
 * @author aarishramesh
 *
 */
public class SensorControllerTest {

	private SensorController motionController;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Nothing to do. Individual tests will ensure the creation.
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		motionController = null;
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.SensorController#SensorController(com.hotel.automation.model.SensorInput, com.hotel.automation.controller.PowerController)}
	 * .
	 */
	@Test
	public void testSensorController() {
		motionController = new SensorController(new SensorInput(1, 1, false), new PowerController(
				new Hotel.HotelBuilder("New", 1, 2, 2).build()));
		assertNotNull("Motion is not generated!", motionController.getSensorInput());
		assertTrue("Floor number is incorrect!", motionController.getSensorInput()
				.getFloorNumber() == 1);
		assertTrue("SubCorridor number is incorrect!", motionController
				.getSensorInput().getSubCorridorNumber() == 1);
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.SensorController#raiseMotionDetectedEvent()}
	 * .
	 */
	@Test
	public void testRaiseMotionDetectedEvent() {
		Hotel hotel = new Hotel.HotelBuilder("New", 2, 2, 2).build();
		motionController = new SensorController(new SensorInput(1, 1, true), 
				new PowerController(hotel));
		motionController.raiseMotionDetectedEvent();
		assertTrue("Not switched on!", hotel.getFloors().get(0)
				.getSubCorridors().get(0).getLightBulbs().get(0).isSwitchedOn());
		assertFalse("Accidentally switched on!", hotel.getFloors().get(1)
				.getSubCorridors().get(0).getLightBulbs().get(0).isSwitchedOn());
		motionController.setSensorInput(new SensorInput(1, 1, false));
		motionController.raiseMotionDetectedEvent();
		assertFalse("Still switched on!", hotel.getFloors().get(0)
				.getSubCorridors().get(0).getLightBulbs().get(0).isSwitchedOn());
	}

}
