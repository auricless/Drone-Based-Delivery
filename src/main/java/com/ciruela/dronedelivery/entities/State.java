package com.ciruela.dronedelivery.entities;

public enum State {
	
	IDLE("IDLE"), 
	LOADING("IDLE"), 
	LOADED("IDLE"), 
	DELIVERING("IDLE"), 
	DELIVERED("IDLE"), 
	RETURNING("IDLE");
	
	private String value;
	
	State(String value){
		this.value = value;
	}

}
