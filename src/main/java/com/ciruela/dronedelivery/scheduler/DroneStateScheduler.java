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

	@Scheduled(fixedRate = 10000)
	public void updateDroneStates() {
		try {
			Iterable<Drone> drones = droneService.findAll();

			for (Drone drone : drones) {
				updateDroneState(drone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateDroneState(Drone drone) throws Exception {
		switch (drone.getState()) {
			case IDLE:
				if (droneCanLoad(drone)) {
					System.out.println(String.format("Drone %s is now LOADING", drone.getSerialNumber()));
					droneService.updateDroneState(drone.getId(), State.LOADING);
				}
				break;
			case LOADING:
				if (droneIsLoaded(drone)) {
					System.out.println(String.format("Drone %s is now LOADED", drone.getSerialNumber()));
					droneService.updateDroneState(drone.getId(), State.LOADED);
				}
				break;
			case LOADED:
				if (droneCanDeliver(drone)) {
					System.out.println(String.format("Drone %s is now DELIVERING", drone.getSerialNumber()));
					droneService.updateDroneState(drone.getId(), State.DELIVERING);
				}
				break;
			case DELIVERING:
				if (droneDelivered(drone)) {
					System.out.println(String.format("Drone %s is now DELIVERED", drone.getSerialNumber()));
					droneService.updateDroneState(drone.getId(), State.DELIVERED);
				}
				break;
			case DELIVERED:
				if (droneCanReturn(drone)) {
					System.out.println(String.format("Drone %s is now RETURNING", drone.getSerialNumber()));
					droneService.updateDroneState(drone.getId(), State.RETURNING);
					droneService.updateDroneBattery(drone.getId(), drone.getBattery() - 20f);
				}
				break;
			case RETURNING:
				if (droneReturned(drone)) {
					System.out.println(String.format("Drone %s is now IDLE", drone.getSerialNumber()));
					droneService.updateDroneState(drone.getId(), State.IDLE);
				}
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

	private boolean droneIsLoaded(Drone drone) {
		return true;
	}

	private boolean droneCanDeliver(Drone drone) {
		return true;
	}

	private boolean droneDelivered(Drone drone) {
		return true;
	}

	private boolean droneCanReturn(Drone drone) {
		return true;
	}

	private boolean droneReturned(Drone drone) {
		return true;
	}
}
