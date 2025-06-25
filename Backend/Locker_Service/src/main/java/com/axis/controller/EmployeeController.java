package com.axis.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Account;
import com.axis.entity.Locker;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;

@RestController
//@CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
@RequestMapping("/locker/employee")
public class EmployeeController {
	
	@Autowired
	private UserDetailsServiceImpl service;
	
	@GetMapping("/pending-locker-requests")
	public List<Locker> pendingLockerRequests() {
		return service.pendingLockerRequests();
	}
	
	@GetMapping("/closing-locker-requests")
	public List<Locker> closingLockerRequests() {
		return service.closingLockerRequests();
	}
	

	@PutMapping("/activate-locker")
	public String activateLocker(@RequestBody Map<String, Object> requestData) {
	    int lockerid = Integer.valueOf(requestData.get("lockerid").toString());
	    Locker locker = service.findLockerById(lockerid);
	    
	    Users user = service.findUserById(locker.getAccount().getUserid());
	    int userid=user.getId();

	    // Check if the locker exists
	    if (locker == null) {
	        return "Locker activation failed. Locker not found.";
	    }

	    // Check if the locker is already active
	    if (locker.getLockerstatus().equalsIgnoreCase("ACTIVE")) {
	        return "Locker " + lockerid + " is already active.";
	    }

	    // Check if the locker amount is paid by the customer
	    if (!locker.isAmountPaid()) {
	        // Sending email to customer to pay the locker amount
	        String recipientEmail = user.getEmail(); // Use the email from the user
	        String subject = "Reminder: Pay Locker Amount";
	        String messageBody = "Dear customer,\n\n"
	                + "This is a reminder to pay the locker amount for Locker ID: " + lockerid + ".\n\n"
	                + "Please make the payment at your earliest convenience to activate the locker.\n\n"
	                + "Regards,\n"
	                + "Bank";

	        sendEmail(recipientEmail, subject, messageBody);

	        return "Locker " + lockerid + " cannot be activated until the customer pays the locker amount. " +
	                "An email notification has been sent to the customer.";
	    }

	    // Activate the locker
	    service.activateLocker(lockerid);

	

	    // Sending activation successful email
	    String recipientEmail = user.getEmail(); // Use the email from the user
	    String subject = "Locker Activation Confirmation";
	    String messageBody = "Dear customer,\n\n"
	            + "Your locker has been successfully activated.\n\n"
	            + "Locker ID: " + lockerid + "\n\n"
	            + "You can now start using your locker for storing your valuables.\n\n"
	            + "Regards,\n"
	            + " Bank";

	    sendEmail(recipientEmail, subject, messageBody);

	    return "Locker " + lockerid + " is successfully activated now. An activation confirmation email has been sent.";
	}


// Method to send an email using JavaMail
private void sendEmail(String recipientEmail, String subject, String messageBody) {
    String senderEmail = "dummyahealthcare22@gmail.com";
    String senderPassword = "Dummy123!"; // Replace with the actual password

    Properties properties = System.getProperties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "465");

    Session session = Session.getInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    });

    session.setDebug(true);

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setText(messageBody);

        Transport.send(message);
    } catch (MessagingException e) {
        // Handle the error if the email fails to send
        e.printStackTrace();
    }
}


	
	@GetMapping("/all-lockers")
	public List<Locker> getAllLockers() {
		return service.getAllLockers();
	}
	
	@PutMapping("/close-locker")
	public String foreCloseLocker(@RequestBody Map<String, Object> requestData) {
		int lockerid = Integer.valueOf(requestData.get("lockerid").toString());
		service.foreCloseLocker(lockerid);
		return "Locker is successfully closed now";
	}
	
}
