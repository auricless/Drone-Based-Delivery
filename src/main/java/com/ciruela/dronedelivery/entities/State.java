package com.ciruela.dronedelivery.entities;

public enum State {
	
	IDLE("IDLE"), 
	LOADING("LOADING"), 
	LOADED("LOADED"), 
	DELIVERING("DELIVERING"), 
	DELIVERED("DELIVERED"), 
	RETURNING("RETURNING");
	
	private String value;
	
	State(String value){
		this.value = value;
	}

}
