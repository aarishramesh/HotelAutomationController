/**
 * 
 */
package com.hotel.automation.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hotel.automation.model.Floor;
import com.hotel.automation.model.Hotel;

/**
 * @author aarishramesh
 *
 */
public class PowerConsumptionCriteriaTest {
	
	private PowerConsumptionCriteria powerConsumptionCriteria;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		powerConsumptionCriteria = new PowerConsumptionCriteria();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.hotel.automation.criteria.PowerConsumptionCriteria#criteriaMetFor(com.hotel.automation.model.Floor)}.
	 */
	@Test
	public void testCriteriaMetFor() {
		Hotel hotel = new Hotel.HotelBuilder("New", 1, 1, 1).build();
		Floor floor = hotel.getFloors().get(0);
		assertTrue("Criteria is not met!", powerConsumptionCriteria.criteriaMetFor(floor));
		floor.getSubCorridors().get(0).getAirConditioners().get(0).setSwitchedOn(true);
		floor.getSubCorridors().get(0).getLightBulbs().get(0).setSwitchedOn(true);
		floor.getSubCorridors().get(0).getDecorativeLamps().get(0).setSwitchedOn(true);
		assertFalse("Criteria is somehow met!", powerConsumptionCriteria.criteriaMetFor(floor));
	}

}
