package com.ciruela.dronedelivery.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ciruela.dronedelivery.entities.Drone;
import com.ciruela.dronedelivery.entities.State;
import com.ciruela.dronedelivery.services.DroneService;

@Component
public class DroneStateScheduler {

	private final DroneService droneService;

	public DroneStateScheduler(DroneService droneService) {
		this.droneService = droneService;
	}

	@Scheduled(fixedRate = 5000)
	public void updateDroneStates() {
		try {
			Iterable<Drone> drones = droneService.findAll();

			for (Drone drone : drones) {
				updateDroneState(drone);
			}
			
			System.out.println("===================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateDroneState(Drone drone) throws Exception {
		switch (drone.getState()) {
			case IDLE:
				if (droneCanLoad(drone)) {
					System.out.println(String.format("Drone %s is now LOADING with battery %s", drone.getSerialNumber(), drone.getBattery()));
					droneService.updateDroneState(drone.getId(), State.LOADING);
				}
				break;
			case LOADING:
				System.out.println(String.format("Drone %s is now LOADED with battery %s", drone.getSerialNumber(), drone.getBattery()));
				droneService.updateDroneState(drone.getId(), State.LOADED);
				break;
			case LOADED:
				System.out.println(String.format("Drone %s is now DELIVERING with battery %s", drone.getSerialNumber(), drone.getBattery()));
				droneService.updateDroneState(drone.getId(), State.DELIVERING);
				break;
			case DELIVERING:
				System.out.println(String.format("Drone %s is now DELIVERED with battery %s", drone.getSerialNumber(), drone.getBattery()));
				droneService.updateDroneState(drone.getId(), State.DELIVERED);
				break;
			case DELIVERED:
				System.out.println(String.format("Drone %s is now RETURNING with battery %s", drone.getSerialNumber(), drone.getBattery()));
				droneService.updateDroneState(drone.getId(), State.RETURNING);
				break;
			case RETURNING:
				System.out.println(String.format("Drone %s is now IDLE with battery %s", drone.getSerialNumber(), drone.getBattery()));
				droneService.updateDroneState(drone.getId(), State.IDLE);
				droneService.updateDroneBattery(drone.getId(), drone.getBattery() - 20f);
				break;
			}
	}

	private boolean droneCanLoad(Drone drone) {
		try {
			return droneService.isDroneAvailable(drone.getId());
		} catch (Exception e) {
			return false;
		}
	}
}
