package com.jsp.HealthcareAppointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.HealthcareAppointment.dto.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	List<Doctor> findBySpecialization(String specialization);

}
