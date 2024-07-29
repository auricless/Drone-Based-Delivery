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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
	
	@Column(length = 100, nullable = false, unique = true)
	@Size(max = 100)
	private String serialNumber;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private Model model;
	
	@Max(1000)
    @PositiveOrZero
	@Column(nullable = false)
	private Integer weightLimit;
	
	@Min(0)
    @Max(100)
    @PositiveOrZero
	@Column(nullable = false)
	private Float battery;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private State state;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Medication> medications;

}
