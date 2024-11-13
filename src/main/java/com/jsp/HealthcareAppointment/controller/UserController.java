package com.jsp.HealthcareAppointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.HealthcareAppointment.dto.User;
import com.jsp.HealthcareAppointment.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/account")
	public String loadRegister(ModelMap map) {
		return userService.loadRegister(map);
	}

	@PostMapping("/account")
	public String createAccount(@Valid User user, BindingResult result, HttpSession session) {
		return userService.createAccount(user, result, session);
	}

	@GetMapping("/otp")
	public String otpSubmit() {
		return "userOtp.html";
	}

	@PostMapping("/otp/{id}")
	public String otpSubmit(@PathVariable int id, @RequestParam int otp, HttpSession session) {
		return userService.otpSubmit(id, otp, session);
	}

	@GetMapping("/resendOtp/{id}")
	public String resendOtp(@PathVariable int id, HttpSession session) {
		return userService.resendOtp(id, session);
	}

	@GetMapping("/login")
	public String loadLogin() {
		return "login.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
		return userService.login(email, password, session);
	}

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return userService.loadHome(session);
	}

	@GetMapping("/update")
	public String update(HttpSession session, ModelMap map) {
		return userService.update(session, map);
	}

	@PostMapping("/update")
	public String update(@Valid User user, BindingResult result, HttpSession session, ModelMap map) {
		return userService.update(user, session, map, result);
	}

	@GetMapping("/fetch")
	public String entrySpl(HttpSession session) {
		return userService.entrySpl(session);
	}

	@PostMapping("/fetch")
	public String entrySpl(HttpSession session, @RequestParam String specialization, ModelMap map) {
		return userService.entrySpl(session, specialization, map);
	}

	@GetMapping("/appointmentBooking/{doc_id}")
	public String appointmentBooking(@PathVariable int doc_id, HttpSession session, ModelMap map) {
		return userService.appointmentBooking(doc_id, session, map);
	}

	@PostMapping("/booking")
	public String booking(HttpSession session, @RequestParam int doctor_id, @RequestParam String date) {
		return userService.booking(session, date, doctor_id);
	}

	@GetMapping("/viewAppointment")
	public String viewAppointment(HttpSession session, ModelMap map) {
		return userService.viewAppointment(session, map);
	}

	@GetMapping("/cancelBooking/{id}")
	public String cancelBooking(@PathVariable int id, HttpSession session) {
		return userService.cancelBooking(id, session);
	}

}
