package com.jsp.HealthcareAppointment.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Component
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doctor_id;
	@Size(min = 3, max = 10, message = "*enter proper name")
	private String name;
	@Size(min = 3, max = 10, message = "*enter proper specialization ")
	private String specialization;
	@Min(value = 2, message = "Enter in number")
	@Max(value = 50, message = "Enter in number")
	private int experience;
	@Size(min = 3, max = 40, message = "*enter proper availability schedule")
	private String aval_sche;
}
