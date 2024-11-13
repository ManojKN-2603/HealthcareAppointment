package com.jsp.HealthcareAppointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.HealthcareAppointment.dto.AppointmentBooking;

public interface AppointmentRepository extends JpaRepository<AppointmentBooking, Integer> {

}
