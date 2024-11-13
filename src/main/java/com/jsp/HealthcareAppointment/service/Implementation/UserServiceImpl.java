package com.jsp.HealthcareAppointment.service.Implementation;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.jsp.HealthcareAppointment.dto.AppointmentBooking;
import com.jsp.HealthcareAppointment.dto.Doctor;
import com.jsp.HealthcareAppointment.dto.User;
import com.jsp.HealthcareAppointment.helper.AES;
import com.jsp.HealthcareAppointment.helper.MyEmailSender;
import com.jsp.HealthcareAppointment.repository.AppointmentRepository;
import com.jsp.HealthcareAppointment.repository.DoctorRepository;
import com.jsp.HealthcareAppointment.repository.UserRepository;
import com.jsp.HealthcareAppointment.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	MyEmailSender emailSender;

	@Autowired
	User user;

	@Autowired
	Doctor doctor;

	@Autowired
	AppointmentBooking appointmentBooking;

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public String loadRegister(ModelMap map) {
		map.put("user", user);
		return "userAccount.html";
	}

	@Override
	public String createAccount(@Valid User user, BindingResult result, HttpSession session) {
		if (!user.getPassword().equals(user.getConfirmpassword()))
			result.rejectValue("confirmpassword", "error:confirmpassword", "*Password mismatch");
		if (userRepository.existsByEmail(user.getEmail()))
			result.rejectValue("email", "error:email", "*Email already exist");
		if (userRepository.existsByMobile(user.getMobile()))
			result.rejectValue("mobile", "error:mobile", "*Mobile Number already exist");
		if (result.hasErrors()) {
			return "userAccount.html";
		} else {
			int otp = new Random().nextInt(1000, 10000);
			user.setOtp(otp);
			emailSender.sendOtp(user);
			user.setPassword(AES.encrypt(user.getPassword(), "123"));
			userRepository.save(user);
			session.setAttribute("success", "Otp sent Successfully");
			session.setAttribute("id", user.getUser_id());
			return "redirect:/user/otp";
		}
	}

	@Override
	public String otpSubmit(int id, int otp, HttpSession session) {
		User user = userRepository.findById(id).orElseThrow();
		if (user.getOtp() == otp) {
			session.setAttribute("id", user.getUser_id());
			user.setVerified(true);
			userRepository.save(user);
			session.setAttribute("success", "Account Created Successfully");
			return "redirect:/";
		} else {
			session.setAttribute("failure", "invalid otp entry");
			session.setAttribute("id", user.getUser_id());
			return "redirect:/user/otp";
		}
	}

	@Override
	public String resendOtp(int id, HttpSession session) {
		User user = userRepository.findById(id).orElseThrow();
		int otp = new Random().nextInt(1000, 10000);
		user.setOtp(otp);
		emailSender.sendOtp(user);
		userRepository.save(user);
		session.setAttribute("success", "Otp sent Successfully");
		session.setAttribute("id", user.getUser_id());
		return "redirect:/user/otp";
	}

	@Override
	public String login(String email, String password, HttpSession session) {
		User user = userRepository.findByEmail(email);
		if (user.getEmail().equals(email) && AES.decrypt(user.getPassword(), "123").equals(password)) {
			session.setAttribute("user", user);
			session.setAttribute("success", "Login success");
			return "redirect:/user/home";
		} else {
			session.setAttribute("failure", "Login failure");
			return "redirect:/user/login";
		}
	}

	@Override
	public String loadHome(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "userHome.html";
		} else {
			session.setAttribute("failure", "Invalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String update(HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			User userUpdate = (User) session.getAttribute("user");
			userUpdate.setPassword(AES.decrypt(userUpdate.getPassword(), "123"));
			map.put("user", userUpdate);
			return "userUpdate.html";
		} else {
			session.setAttribute("failure", "Invalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String update(User updatedUser, HttpSession session, ModelMap map, BindingResult result) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if (result.hasErrors()) {
				return "userUpdate.html";
			} else {
				if (!(user.getEmail().equals(updatedUser.getEmail()))
						|| !(user.getPassword().equals(updatedUser.getPassword()))) {
					int otp = new Random().nextInt(1000, 10000);
					user.setOtp(otp);
					emailSender.sendOtp(user);
					user.setEmail(updatedUser.getEmail());
					user.setMobile(updatedUser.getMobile());
					user.setName(updatedUser.getName());
					user.setPassword(AES.encrypt(updatedUser.getPassword(), "123"));
					user.setConfirmpassword(AES.encrypt(updatedUser.getPassword(), "123"));
					userRepository.save(user);
					session.setAttribute("success", "Otp sent Successfully");
					session.setAttribute("id", user.getUser_id());
					return "redirect:/user/otp";
				} else {
					user.setMobile(updatedUser.getMobile());
					user.setName(updatedUser.getName());
					user.setPassword(AES.encrypt(user.getPassword(), "123"));
					userRepository.save(user);
					session.setAttribute("success", "updated Successfully");
					return "redirect:/user/home";
				}
			}
		} else {
			session.setAttribute("failure", "sInvalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String entrySpl(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "userFecth.html";
		} else {
			session.setAttribute("failure", "*Invalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String entrySpl(HttpSession session, String specialization, ModelMap map) {
		if (session.getAttribute("user") != null) {
			List<Doctor> doctor = doctorRepository.findBySpecialization(specialization);
			if (doctor.isEmpty()) {
				session.setAttribute("failure", "No Doctor found on this specialization ");
				return "appointment.html";
			} else {
				map.put("doctor", doctor);
				return "appointment.html";
			}
		} else {
			session.setAttribute("failure", "Invalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String appointmentBooking(int doc_id, HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			Doctor doctor = doctorRepository.findById(doc_id).orElseThrow();
			map.put("doctor", doctor);
			return "booking.html";
		} else {
			return "redirect:/user/login";
		}
	}

	@Override
	public String booking(HttpSession session, String date, int doctor_id) {
		if (session.getAttribute("user") != null) {
			Doctor doctor = doctorRepository.findById(doctor_id).orElseThrow();
			User user = (User) session.getAttribute("user");
			appointmentBooking.setDate(date);
			appointmentBooking.setDoctor(doctor);
			appointmentBooking.setUser(user);
			appointmentRepository.save(appointmentBooking);
			session.setAttribute("success", "Appointment booked Successfully");
			return "userHome.html";
		} else {
			session.setAttribute("failure", "Invalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String viewAppointment(HttpSession session, ModelMap map) {
		if (session.getAttribute("user") != null) {
			List<AppointmentBooking> appointmentBooking = appointmentRepository.findAll();
			map.put("appointments", appointmentBooking);
			return "userView.html";
		} else {
			session.setAttribute("failure", "Invalid credintials");
			return "redirect:/user/login";
		}
	}

	@Override
	public String cancelBooking(int id, HttpSession session) {
		if (session.getAttribute("user") != null) {
			AppointmentBooking booking = appointmentRepository.findById(id).orElseThrow();
			booking.setDoctor(null);
			booking.setUser(null);
			appointmentRepository.delete(booking);
			session.setAttribute("success", "Appointment Cancel Successfully");
			return "redirect:/user/viewAppointment";
		} else {
			session.setAttribute("failure", "*Invalid credintials");
			return "redirect:/user/login";
		}
	}

}
