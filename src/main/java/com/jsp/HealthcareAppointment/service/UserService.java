package com.jsp.HealthcareAppointment.service;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.jsp.HealthcareAppointment.dto.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface UserService {

	String loadRegister(ModelMap map);

	String createAccount(@Valid User user, BindingResult result, HttpSession session);

	String login(String email, String password, HttpSession session);

	String appointmentBooking(int doc_id, HttpSession session, ModelMap map);

	String otpSubmit(int id, int otp, HttpSession session);

	String resendOtp(int id, HttpSession session);

	String update(HttpSession session, ModelMap map);

	String loadHome(HttpSession session);

	String update(User user, HttpSession session, ModelMap map, BindingResult result);

	String booking(HttpSession session, String date, int id);

	String viewAppointment(HttpSession session, ModelMap map);

	String cancelBooking(int id, HttpSession session);

	String entrySpl(HttpSession session);

	String entrySpl(HttpSession session, String specialization, ModelMap map);
}
