package com.ciruela.dronedelivery.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ciruela.dronedelivery.entities.Drone;
import com.ciruela.dronedelivery.entities.Medication;
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
	public ResponseEntity<Void> registerDrone(@Valid @RequestBody Drone drone, UriComponentsBuilder ucb){
		Drone registerNewDrone = service.registerNewDrone(drone);
		URI locationOfRegisteredDrone = ucb
	            .path("/api/v1/drone/{id}")
	            .buildAndExpand(registerNewDrone.getId())
	            .toUri();
		
	    return ResponseEntity.created(locationOfRegisteredDrone).build();
	}
	
	@PutMapping("/api/v1/drone/{droneId}/loadMedication")
	public ResponseEntity<Drone> loadMedicationsToDrone(@PathVariable Long droneId, @RequestBody List<Medication> medications){
		try {
			service.loadDroneWithMedication(medications, droneId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/v1/drone/{droneId}/medications")
	public ResponseEntity<List<Medication>> getLoadedMedicationsByDrone(@PathVariable Long droneId){
		try {
			List<Medication> medicationsByDrone = service.getMedicationsByDrone(droneId);
			return ResponseEntity.ok(medicationsByDrone);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/v1/drone/{droneId}/available")
	public ResponseEntity<Boolean> isDroneAvailableForLoading(@PathVariable Long droneId){
		try {
			boolean isDroneAvailable = service.isDroneAvailable(droneId);
			return ResponseEntity.ok(isDroneAvailable);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/api/v1/drone/{droneId}/battery")
	public ResponseEntity<Float> getDroneBatteryCapacity(@PathVariable Long droneId){
		try {
			float droneBatteryCapacity = service.getDroneBatteryCapacity(droneId);
			return ResponseEntity.ok(droneBatteryCapacity);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
