package com.ciruela.dronedelivery.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ciruela.dronedelivery.entities.Drone;

@Repository
interface DroneRepository extends CrudRepository<Drone, Long> {

}
