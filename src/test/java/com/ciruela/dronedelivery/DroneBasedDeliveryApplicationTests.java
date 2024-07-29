package com.ciruela.dronedelivery;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ciruela.dronedelivery.entities.Drone;
import com.ciruela.dronedelivery.entities.Medication;
import com.ciruela.dronedelivery.entities.Model;
import com.ciruela.dronedelivery.entities.State;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DroneBasedDeliveryApplicationTests {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	void shouldCreateNewDroneOnRegisterDrone() {
		Drone actualDrone = Drone.builder()
			.model(Model.HEAVY_WEIGHT)
			.serialNumber("DRONE011")
			.battery(75f)
			.medications(Collections.emptyList())
			.weightLimit(100)
			.state(State.IDLE)
			.build();
		
		Drone expectedDrone = Drone.builder()
			.model(Model.HEAVY_WEIGHT)
			.serialNumber("DRONE011")
			.battery(75f)
			.medications(Collections.emptyList())
			.weightLimit(100)
			.state(State.IDLE)
			.id(11L)
			.build();
		
		ResponseEntity<String> createResponse = restTemplate.postForEntity("/api/v1/drone/", actualDrone, String.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		URI location = createResponse.getHeaders().getLocation();
		ResponseEntity<String> responseDrone = restTemplate.getForEntity(location, String.class);
		assertThat(responseDrone.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(responseDrone.getBody());
		Long id = documentContext.read("$.id", Long.class);
		String model = documentContext.read("$.model");
		String serialNumber = documentContext.read("$.serialNumber");
		Float battery = documentContext.read("$.battery", Float.class);
		List<Medication> medications = documentContext.read("$.medications");
		
		assertThat(id).isEqualTo(expectedDrone.getId());
		assertThat(model).isEqualTo(expectedDrone.getModel().toString());
		assertThat(serialNumber).isEqualTo(expectedDrone.getSerialNumber());
		assertThat(battery).isEqualTo(expectedDrone.getBattery());
		assertThat(medications).hasSize(0);
	}
	
	@Test
	void shouldReturnDroneWithMedicationsLoaded() {
		Medication medication1 = Medication.builder().id(Long.valueOf(1))
				.code("Ibuprofen")
				.image("/images/ibuprofen.jpg")
				.weight(30)
				.build();
		
		Medication medication2 = Medication.builder().id(Long.valueOf(2))
				.code("Amoxicillin")
				.image("/images/amoxicillin.jpg")
				.weight(30)
				.build();
		
		Medication medication3 = Medication.builder().id(Long.valueOf(3))
				.code("Omeprazole")
				.image("/images/omeprazole.jpg")
				.weight(30)
				.build();
		
		List<Medication> loadedMedications = new ArrayList<>();
		loadedMedications.add(medication1);
		loadedMedications.add(medication2);
		loadedMedications.add(medication3);
		
		HttpEntity<List<Medication>> request = new HttpEntity<>(loadedMedications);
		ResponseEntity<String> response = restTemplate.exchange("/api/v1/drone/{id}/loadMedication", HttpMethod.PUT, request, String.class, 1L);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Long id = documentContext.read("$.id", Long.class);
		String model = documentContext.read("$.model");
		String serialNumber = documentContext.read("$.serialNumber");
		Float battery = documentContext.read("$.battery", Float.class);
		List<Map<String, Object>> medicationsListOfMap = documentContext.read("$.medications");
		
		List<Medication> medications = medicationsListOfMap.stream()
	    .map(medicationMap -> {
	        Medication medication = new Medication();
	        medication.setId(((Number) medicationMap.get("id")).longValue());
	        medication.setName((String) medicationMap.get("name"));
	        medication.setWeight((Integer) medicationMap.get("weight"));
	        medication.setCode((String) medicationMap.get("code"));
	        medication.setImage((String) medicationMap.get("image"));
	        return medication;
	    }).collect(Collectors.toList());
		
		assertThat(id).isEqualTo(1L);
		assertThat(model).isEqualTo(Model.LIGHT_WEIGHT.toString());
		assertThat(serialNumber).isEqualTo("DRONE001");
		assertThat(battery).isEqualTo(100.0f);
		assertThat(medications).isNotNull();
	    assertThat(medications).hasOnlyElementsOfType(Medication.class);
	    assertThat(medications).hasSize(3);
	}
	
	@Test
	void shouldReturnMedicationByDrone() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/drone/{id}/medications", String.class, 3L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		List<Map<String, Object>> medicationsListOfMap = documentContext.read("$");
		
		List<Medication> medications = medicationsListOfMap.stream()
	    .map(medicationMap -> {
	        Medication medication = new Medication();
	        medication.setId(((Number) medicationMap.get("id")).longValue());
	        medication.setName((String) medicationMap.get("name"));
	        medication.setWeight((Integer) medicationMap.get("weight"));
	        medication.setCode((String) medicationMap.get("code"));
	        medication.setImage((String) medicationMap.get("image"));
	        return medication;
	    }).collect(Collectors.toList());
		
		assertThat(medications).isNotNull();
	    assertThat(medications).hasOnlyElementsOfType(Medication.class);
	    assertThat(medications).hasSize(3);
	}
	
	@Test
	void shouldReturnAvailableDroneForLoading() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/drone/{id}/available", String.class, 1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		boolean isAvailable = documentContext.read("$", Boolean.class);
		
		assertThat(isAvailable).isEqualTo(false);
	}
	
	@Test
	void shouldReturnDroneInformation() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/drone/{id}/battery", String.class, 1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Float battery = documentContext.read("$", Float.class);
		
		assertThat(battery).isNotNull();
		assertThat(battery).isEqualTo(100.0f);
	}

}
