package com.ciruela.dronedelivery;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DroneBasedDeliveryApplicationTests {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	void shouldCreateNewDroneOnRegisterDrone() {
		Drone actualDrone = Drone.builder()
			.id(Long.valueOf(1))
			.model(Model.HEAVY_WEIGHT)
			.serialNumber("56437865798436")
			.battery(75f)
			.medications(Collections.emptyList())
			.build();
		
		Drone expectedDrone = Drone.builder()
				.id(Long.valueOf(1))
				.model(Model.HEAVY_WEIGHT)
				.serialNumber("56437865798436")
				.battery(75f)
				.medications(Collections.emptyList())
				.state(State.IDLE)
				.build();
		
		ResponseEntity<String> createResponse = restTemplate.postForEntity("/api/v1/drone/", actualDrone, String.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		URI location = createResponse.getHeaders().getLocation();
		ResponseEntity<String> responseDrone = restTemplate.getForEntity(location, String.class);
		assertThat(responseDrone).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(responseDrone.getBody());
		Number id = documentContext.read("$.id");
		String model = documentContext.read("$.model");
		Long serialNumber = documentContext.read("$.serialNumber");
		Float battery = documentContext.read("$.battery");
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
				.code("YGKL54PO4")
				.image("temp-1.JPEG")
				.weight(30)
				.build();
		
		Medication medication2 = Medication.builder().id(Long.valueOf(2))
				.code("YGKL54PO4")
				.image("temp-2.JPEG")
				.weight(30)
				.build();
		
		Medication medication3 = Medication.builder().id(Long.valueOf(3))
				.code("YGKL54PO4")
				.image("temp-3.JPEG")
				.weight(30)
				.build();
		
		List<Medication> loadedMedications = new ArrayList<>();
		loadedMedications.add(medication1);
		loadedMedications.add(medication2);
		loadedMedications.add(medication3);
		
		Drone actualDrone = Drone.builder()
				.id(Long.valueOf(1))
				.model(Model.HEAVY_WEIGHT)
				.serialNumber("56437865798436")
				.battery(75f)
				.medications(Collections.emptyList())
				.build();
		
		HttpEntity<Drone> request = new HttpEntity<>(actualDrone);
		ResponseEntity<String> response = restTemplate.exchange("/api/v1/drone/{id}/loadMedication", HttpMethod.PUT, request, String.class, 1L);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		String model = documentContext.read("$.model");
		Long serialNumber = documentContext.read("$.serialNumber");
		Float battery = documentContext.read("$.battery");
		List<Medication> medications = documentContext.read("$.medications");
		
		assertThat(id).isEqualTo(1L);
		assertThat(model).isEqualTo(Model.HEAVY_WEIGHT.toString());
		assertThat(serialNumber).isEqualTo("56437865798436");
		assertThat(battery).isEqualTo("75f");
		assertThat(medications).isNotNull();
	    assertThat(medications).hasOnlyElementsOfType(Medication.class);
	    assertThat(medications).hasSize(0);
	}
	
	@Test
	void shouldReturnMedicationByDrone() {
		ResponseEntity<String> response = restTemplate.getForEntity("api/v1/drone/{id}/medications", String.class, 1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		List<Medication> medications = documentContext.read("$.medications");
		
		assertThat(medications).isNotNull();
		assertThat(medications).hasSize(3);
		assertThat(medications).hasExactlyElementsOfTypes(Medication.class);
	}
	
	@Test
	void shouldReturnAvailableDroneForLoading() {
		ResponseEntity<String> response = restTemplate.getForEntity("api/v1/drone/{id}/medications", String.class, 1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		boolean isAvailable = documentContext.read("$.isAvailable");
		
		assertThat(isAvailable).isEqualTo(true);
	}
	
	@Test
	void shouldReturnDroneInformation() {
		ResponseEntity<String> response = restTemplate.getForEntity("api/v1/drone/{id}/medications", String.class, 1L);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Float battery = documentContext.read("$.battery");
		
		assertThat(battery).isNotNull();
		assertThat(battery).isEqualTo(75f);
	}

}
