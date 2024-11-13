package com.jsp.HealthcareAppointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsp.HealthcareAppointment.dto.Doctor;
import com.jsp.HealthcareAppointment.service.DoctorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	DoctorService doctorService;

	@GetMapping("/register")
	public String loadRegister(ModelMap map) {
		return doctorService.loadRegister(map);
	}

	@PostMapping("/register")
	public String createAccount(@Valid Doctor doctor, BindingResult result, HttpSession session) {
		return doctorService.createAccount(doctor, session, result);
	}
}
