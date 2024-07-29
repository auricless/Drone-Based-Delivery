package com.ciruela.dronedelivery.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class Drone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100)
	private String serialNumber;
	
	@Enumerated(EnumType.STRING)
	private Model model;
	
	@Column(length = 1000)
	private Integer weight;
	
	@Column(length = 100)
	private Float battery;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Medication> medications;

}
