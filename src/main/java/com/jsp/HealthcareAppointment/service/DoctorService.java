package com.jsp.HealthcareAppointment.service;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.jsp.HealthcareAppointment.dto.Doctor;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface DoctorService {

	String loadRegister(ModelMap map);

	String createAccount(@Valid Doctor doctor, HttpSession session, BindingResult result);

}
