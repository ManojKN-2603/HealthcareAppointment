package com.jsp.HealthcareAppointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.HealthcareAppointment.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

}
