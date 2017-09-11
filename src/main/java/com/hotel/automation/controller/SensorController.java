/**
 * 
 */
package com.hotel.automation.controller;

import java.util.Observable;

import com.hotel.automation.model.SensorInput;

import lombok.Data;

/**
 * Manages the {@link SensorInput} events and registers the {@link PowerController}
 * as an Observer to the {@link Observable} <code>Motion.
 * 
 * @author aarishramesh
 *
 */
@Data
public final class SensorController implements Runnable {

	private SensorInput sensorInput;

	private PowerController powerController;

	/**
	 * Default Constructor.
	 */
	public SensorController(SensorInput sensorInput, PowerController powerController) {
		this.sensorInput = sensorInput;
		this.powerController = powerController;
	}

	/**
	 * Raises an event that a Motion has been detected to all the Observers.
	 * 
	 * @param turnOnSwitch
	 *            Turn on or not.
	 */
	public void raiseMotionDetectedEvent() {
		sensorInput.addObserver(powerController);
		sensorInput.notifyObservers(sensorInput.isMotionDetected());
	}

	/**
	 * @return
	 */
	public SensorInput getSensorInput() {
		return sensorInput;
	}

	@Override
	public void run() {
		raiseMotionDetectedEvent();
	}
}
