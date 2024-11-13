package com.jsp.HealthcareAppointment.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.jsp.HealthcareAppointment.dto.Doctor;
import com.jsp.HealthcareAppointment.repository.DoctorRepository;
import com.jsp.HealthcareAppointment.service.DoctorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	Doctor doctor;

	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public String loadRegister(ModelMap map) {
		map.put("doctor", doctor);
		return "docRegister.html";
	}

	@Override
	public String createAccount(@Valid Doctor doctor, HttpSession session, BindingResult result) {
		if (result.hasErrors()) {
			session.setAttribute("failure", "register is unsuccess");
			return "docRegister.html";
		} else {
			session.setAttribute("success", "register is success");
			doctorRepository.save(doctor);
			return "home.html";
		}
	}

}
