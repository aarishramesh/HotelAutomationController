/**
 * 
 */
package com.hotel.automation.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * Hotel is an entity in this application. Represents a real-life Hotel which
 * will have components associated with it and probably a few actions as well.
 * 
 * Using Builder pattern to construct the Hotel entity
 * 
 * @author aarishramesh
 *
 */
@Data
public class Hotel {

	private List<Floor> floors;

	private String name;

	public Hotel(String name, List<Floor> floors) {
		this.name = name;
		this.floors = floors;
	}
	
	/**
	 * Traverses through the Tree-like <code>Hotel</code> object and prints out
	 * the state of each individual node.
	 */
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		getFloors().forEach(floor -> {
			stringBuilder.append(floor.toString());
			stringBuilder.append(System.lineSeparator());
			floor.getMainCorridors().forEach(mainCorridor -> {
				stringBuilder.append(mainCorridor.toString());
				stringBuilder.append(System.lineSeparator());
				mainCorridor.getLightBulbs().forEach(lightBulb -> {
					stringBuilder.append(lightBulb.toString());
					stringBuilder.append(System.lineSeparator());
				});
				mainCorridor.getAirConditioners().forEach(airConditioner -> {
					stringBuilder.append(airConditioner.toString());
					stringBuilder.append(System.lineSeparator());
				});
			});
			floor.getSubCorridors().forEach(subCorridor -> {
				stringBuilder.append(subCorridor.toString());
				stringBuilder.append(System.lineSeparator());
				subCorridor.getLightBulbs().forEach(lightBulb -> {
					stringBuilder.append(lightBulb.toString());
					stringBuilder.append(System.lineSeparator());
				});
				subCorridor.getAirConditioners().forEach(airConditioner -> {
					stringBuilder.append((airConditioner).toString());
					stringBuilder.append(System.lineSeparator());
				});
			});
		});
		return stringBuilder.toString();
	}

	public static class HotelBuilder {

		private String name;
		private List<Floor> floors = new ArrayList<Floor>();
		
		public HotelBuilder(String name, int noOfFloors, int noOfCorridors
				, int noOfSubcorridors) {
			this.name = name;
			this.addFloors(noOfFloors)
				.addMainCorridors(noOfCorridors)
				.addSubCorridors(noOfSubcorridors);
		}
		
		/**
		 * Adds as many floors to the count as requested.
		 * 
		 * @param floorCount
		 * @return
		 */
		public HotelBuilder addFloors(int floorCount) {
			for (int counter = 0; counter < floorCount; counter++) {
				Floor floor = new Floor(counter);
				floor.setMainCorridors(new ArrayList<MainCorridor>());
				floor.setSubCorridors(new ArrayList<SubCorridor>());
				floors.add(floor);
			}
			return this;
		}

		/**
		 * Adds as many MainCorridors to each floor as requested.
		 * 
		 * @param mainCorridorCount
		 * @return
		 */
		public HotelBuilder addMainCorridors(int mainCorridorCount) {
			int floorCount = floors.size();
			for (int floorCounter = 0; floorCounter < floorCount; floorCounter++) {
				for (int corridorCounter = 0; corridorCounter < mainCorridorCount; corridorCounter++) {
					MainCorridor mainCorridor = new MainCorridor(corridorCounter);
					floors.get(floorCounter).getMainCorridors()
							.add(mainCorridor);
				}
			}
			return this;
		}

		/**
		 * Adds as many SubCorridors to each floor as requested.
		 * 
		 * @param subCorridorCount
		 * @return
		 */
		public HotelBuilder addSubCorridors(int subCorridorCount) {
			int floorCount = floors.size();
			for (int floorCounter = 0; floorCounter < floorCount; floorCounter++) {
				for (int corridorCounter = 0; corridorCounter < subCorridorCount; corridorCounter++) {
					SubCorridor subCorridor = new SubCorridor(corridorCounter);
					floors.get(floorCounter).getSubCorridors()
							.add(subCorridor);
				}
			}
			return this;
		}

		/**
		 * Returns the hotel instance at this given instant.
		 * 
		 * @return
		 */
		public Hotel build() {
			return new Hotel(name, floors);
		}

	}

}
