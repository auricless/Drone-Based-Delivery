package com.ciruela.dronedelivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
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
public class Medication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Pattern(regexp = "^[a-zA-Z0-9_-]+$")
	@Column(nullable = false)
	private String name;
	
	@Column(length = 1000)
	private Integer weight;
	
	@Pattern(regexp = "^[A-Z0-9_]+$")
	@Column(nullable = false, unique = true)
	private String code;
	
	private String image;

}
