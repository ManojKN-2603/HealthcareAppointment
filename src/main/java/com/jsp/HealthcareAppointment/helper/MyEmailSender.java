package com.jsp.HealthcareAppointment.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jsp.HealthcareAppointment.dto.User;

import jakarta.mail.internet.MimeMessage;

@Service
public class MyEmailSender {
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	TemplateEngine templateEngine;

	public void sendOtp(User user) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("manojkn263@gmail.com", "Ecommerce Site");
			helper.setTo(user.getEmail());
			helper.setSubject("Otp for Account Creation");
//			helper.setText("<h1>Your otp is</h1>" + seller.getOtp(),true);
			Context context = new Context();
			context.setVariable("user", user);
			String text = templateEngine.process("userEmail.html", context);
			helper.setText(text, true);
			mailSender.send(message);
			System.out.println(user.getOtp());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
