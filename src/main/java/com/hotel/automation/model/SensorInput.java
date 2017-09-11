/**
 * 
 */
package com.hotel.automation.model;

import java.util.Observable;
import java.util.Observer;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is representative Sensor input keyed into the application. Currently, it keeps of
 * information like at which {@link Floor} and {@link SubCorridor} to turn the
 * {@link LightBulb} on/off.
 * 
 * @author aarishramesh
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SensorInput extends Observable {

	private int floorNumber;

	private int subCorridorNumber;

	private boolean motionDetected;
	
	public SensorInput(int floorNumber, int subCorridorNumber) {
		this.floorNumber = floorNumber;
		this.subCorridorNumber = subCorridorNumber;
	}
	
	/**
	 * Default constructor.
	 */
	public SensorInput(int floorNumber, int subCorridorNumber,
			boolean motionDetected) {
		this.floorNumber = floorNumber;
		this.subCorridorNumber = subCorridorNumber;
		this.motionDetected = motionDetected;
	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
	}

	@Override
	public void notifyObservers() {
		this.notifyObservers(null);
	}

	@Override
	public void notifyObservers(Object object) {
		setChanged();
		super.notifyObservers(object);
	}

}
