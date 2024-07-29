package com.ciruela.dronedelivery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ciruela.dronedelivery.entities.Drone;
import com.ciruela.dronedelivery.entities.Medication;
import com.ciruela.dronedelivery.entities.Model;
import com.ciruela.dronedelivery.entities.State;
import com.ciruela.dronedelivery.repositories.DroneRepository;

import jakarta.validation.Valid;

@Service
public class DroneService {
	
	private final DroneRepository repository;
	
	public DroneService(DroneRepository repository) {
		this.repository = repository;
	}
	
	public Drone registerNewDrone(@Valid Drone drone) {
		return repository.save(drone);
	}
	
	public void loadDroneWithMedication(List<Medication> medications, Long droneId) throws Exception {
		Optional<Drone> droneOptional = repository.findById(droneId);
		if(droneOptional.isPresent()) {
			Drone drone = droneOptional.get();
			
			evaluateMedicationsWeightAgainstDroneWeightLimit(medications, drone);
			
			drone.setMedications(medications);
			drone.setState(State.LOADED);
			repository.save(drone);
		}else {
			throw new Exception("Drone does not exist");
		}
	}

	private void evaluateMedicationsWeightAgainstDroneWeightLimit(List<Medication> medications, Drone drone)
			throws Exception {
		int totalWeight = medications.stream().mapToInt(Medication::getWeight).sum();
		
		if(totalWeight > 1000) {
			throw new Exception("Total medications' weight will overload the drone");
		}
		
		switch (drone.getModel()) {
			case LIGHT_WEIGHT:
				if(totalWeight > Model.LIGHT_WEIGHT.getWeightLimit()) {
					throw new Exception("Total medications' weight will overload the drone");
				}
				break;
			case MIDDLE_WEIGHT:
				if(totalWeight > Model.MIDDLE_WEIGHT.getWeightLimit()) {
					throw new Exception("Total medications' weight will overload the drone");
				}
				break;
			case HEAVY_WEIGHT:
				if(totalWeight > Model.HEAVY_WEIGHT.getWeightLimit()) {
					throw new Exception("Total medications' weight will overload the drone");
				}
				break;
			case CRUISER_WEIGHT:
				if(totalWeight > Model.CRUISER_WEIGHT.getWeightLimit()) {
					throw new Exception("Total medications' weight will overload the drone");
				}
				break;
			default:
				break;
		}
	}
	
	public List<Medication> getMedicationsByDrone(Long droneId) throws Exception{
		Optional<Drone> droneOptional = repository.findById(droneId);
		if(droneOptional.isPresent()) {
			Drone drone = droneOptional.get();
			return drone.getMedications();
		}else {
			throw new Exception("Drone does not exist");
		}
	}
	
	public boolean isDroneAvailable(Long droneId) throws Exception {
		Optional<Drone> droneOptional = repository.findById(droneId);
		if(droneOptional.isPresent()) {
			Drone drone = droneOptional.get();
			return drone.getBattery() > 25f && 
				drone.getState().equals(State.IDLE);
		}else {
			throw new Exception("Drone does not exist");
		}
	}
	
	public float getDroneBatteryCapacity(Long droneId) throws Exception {
		Optional<Drone> droneOptional = repository.findById(droneId);
		if(droneOptional.isPresent()) {
			Drone drone = droneOptional.get();
			return drone.getBattery();
		}else {
			throw new Exception("Drone does not exist");
		}
	}

}
