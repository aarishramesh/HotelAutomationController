/**
 * 
 */
package com.hotel.automation.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class PowerControllerTest {

	private PowerController powerController;

	private Hotel hotel;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hotel = new Hotel.HotelBuilder("New", 2, 2, 2).build();
		powerController = new PowerController(hotel);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerController#PowerController(com.hotel.automation.model.Hotel)}
	 * .
	 */
	@Test
	public void testPowerController() {
		assertEquals(hotel, powerController.getHotel());
	}

	/**
	 * Test method for
	 * {@link com.hotel.automation.controller.PowerController#update(java.util.Observable, java.lang.Object)}
	 * .
	 */
	@Test
	public void testUpdate() {
		powerController.update(new SensorInput(1, 2, true), null);
		assertTrue("Not switched on!", hotel.getFloors().get(0)
				.getSubCorridors().get(1).getLightBulbs().get(0).isSwitchedOn());
		powerController.update(new SensorInput(1, 2, false), null);
		assertFalse("Still switched on!", hotel.getFloors().get(0)
				.getSubCorridors().get(1).getLightBulbs().get(0).isSwitchedOn());

	}

}
