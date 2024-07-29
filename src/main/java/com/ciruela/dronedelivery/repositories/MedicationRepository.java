package com.ciruela.dronedelivery.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ciruela.dronedelivery.entities.Medication;

public interface MedicationRepository extends CrudRepository<Medication, Long> {

}
