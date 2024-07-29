package com.ciruela.dronedelivery.entities;

public enum Model {
	
	LIGHT_WEIGHT(100),
	MIDDLE_WEIGHT(300),
	CRUISER_WEIGHT(500),
	HEAVY_WEIGHT(1000);
	
	private int weightLimit;
	
	Model(int weightLimit) {
		this.weightLimit = weightLimit;
	}
	
	public int getWeightLimit() {
		return weightLimit;
	}

}
