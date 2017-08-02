package com.hotel.automation.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.hotel.automation.controller.PowerController;
import com.hotel.automation.controller.SensorController;
import com.hotel.automation.model.Hotel;
import com.hotel.automation.model.SensorInput;
import com.hotel.automation.util.InitialHotelState;
import com.hotel.automation.util.InputParser;


/**
 * Entry point to Hotel Automation Controller application.
 * <ul>
 * <li>Reads the Sensor input about the {@link Hotel}.
 * <li>Instantiates the {@link PowerController} and prints the initial <code>Hotel state.
 * <li>Instantiates the {@link ThreadPoolExecutor} to  
 * asynchronously process {@link SensorInput}.
 * 
 * @author aarishramesh
 */
public class HotelAutomationControllerApp {

	public static void main(String[] args) {
		System.out
			.println("Welcome to Hotel Automation Application! Please enter the data below.");

		InitialHotelState hotelState = InputParser.parseInitialInputs();
		Hotel hotel = new Hotel.HotelBuilder("New", hotelState.getFloorCount()
				, hotelState.getMainCorridorCount(), hotelState.getSubCorridorCount()).build();

		PowerController powerController = new PowerController(hotel);
		System.out.println(hotel);

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

		while(true) {
			try {
				SensorInput sensorInput = InputParser
						.parseSensorInputs();
				if (sensorInput != null) {
					SensorController sensorController = new SensorController(
							sensorInput, powerController);
					executor.execute(sensorController);
				}

			} catch (IllegalArgumentException exception) {
				System.err.println(exception.getMessage());
			} finally {
				cleanUpOnShutdown(executor);
			}
		}
	}

	/**
	 * Adds a ShutdownHook to the {@link Runtime} which will clean up any
	 * resources used in this application during the JVM Shutdown.
	 * 
	 * @param threadPoolExecutor
	 */
	private static void cleanUpOnShutdown(
			ThreadPoolExecutor threadPoolExecutor) {
		Runtime.getRuntime().addShutdownHook(
				new Thread(() -> threadPoolExecutor.shutdownNow()));
	}
}
