package com.ciruela.dronedelivery.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ciruela.dronedelivery.entities.Drone;
import com.ciruela.dronedelivery.services.DroneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/drone")
public class DroneRestController {
	
	private final DroneService service;
	
	public DroneRestController(DroneService service) {
		this.service = service;
	}
	
	@PostMapping("/")
	private ResponseEntity<Void> registerDrone(@Valid @RequestBody Drone drone, UriComponentsBuilder ucb){
		Drone registerNewDrone = service.registerNewDrone(drone);
		URI locationOfRegisteredDrone = ucb
	            .path("/api/v1/drone/{id}")
	            .buildAndExpand(registerNewDrone.getId())
	            .toUri();
		
	    return ResponseEntity.created(locationOfRegisteredDrone).build();
	}

}
