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
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Locker;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/locker/customer")
public class CustomerController {

    @Autowired
    private UserDetailsServiceImpl service;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/apply-for-locker")
    public String applyForLocker(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUser(email);

        String lockertype = String.valueOf(requestData.get("lockertype").toString());
        String lockersize = String.valueOf(requestData.get("lockersize").toString());
        String lockerlocation = String.valueOf(requestData.get("lockerlocation").toString());

        logger.info("Applying for a locker for user: {}", email);

        service.applyForLocker(user, lockertype, lockerlocation, lockersize);

        // Sending email
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = user.getEmail(); // Use the email from the user
        String subject = "Locker Application Confirmation";
        String messageBody = "Dear customer,\n\n"
                + "Thank you for applying for a locker with Bank.\n\n"
                + "Locker Type: " + lockertype + "\n"
                + "Locker Size: " + lockersize + "\n"
                + "Locker Location: " + lockerlocation + "\n\n"
                + "Your application is under review. We will notify you once it is approved.\n\n"
                + "Regards,\n"
                + "Bank";

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
            logger.error("Error sending the confirmation email for locker application: {}", e.getMessage());
        }

        return "You've successfully applied for " + lockertype + " of size " + lockersize + ". An application confirmation email has been sent.";
    }

    @GetMapping("/my-lockers")
    public List<Locker> getAllMyLockers(HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUser(email);
        int accid = service.findAccId(user.getId());

        logger.info("Fetching all lockers for user: {}", email);

        return service.getAllMyLockers(accid);
    }

    @PutMapping("/locker-payment")
    public String lockerPayment(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUser(email);

        int lockerid=Integer.valueOf(requestData.get("lockerid").toString());
        String lockertype = String.valueOf(requestData.get("lockertype").toString());
        Double lockeramount = Double.valueOf(requestData.get("lockeramount").toString());
        Locker locker = service.findLockerById(lockerid);
        

        // Get the locker price from the database based on the locker type
        Double lockerPrice = locker.getLockerprice();

        // Check if the locker amount matches the locker price
        if (!lockeramount.equals(lockerPrice)) {
            return "Locker payment failed. Please pay the full locker amount: " + lockerPrice;
        }

        // Proceed with the locker payment process and update the locker's amountPaid to true
        boolean paymentSuccess = service.lockerPayment(lockertype, lockeramount, user);
        if (!paymentSuccess) {
            return "Locker payment failed. Please try again later.";
        }

        // Update the locker's amountPaid to true
      
        locker.setAmountPaid(true);
        service.updateLocker(locker);

        // Sending email
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = user.getEmail(); // Use the email from the user
        String subject = "Locker Payment Confirmation";
        String messageBody = "Dear customer,\n\n"
                + "Thank you for making the payment for the locker.\n\n"
                + "Locker Type: " + lockertype + "\n"
                + "Payment Amount: " + lockeramount + "\n\n"
                + "Your payment has been successfully processed.\n\n"
                + "Regards,\n"
                + "Bank";

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
            return "Locker payment successful, but there was an error sending the confirmation email. Please contact customer support.";
        }

        return "You've successfully made the payment for " + lockertype + ". A payment confirmation email has been sent.";
    }


    @PutMapping("/close-locker-request")
    public String closeLockerRequest(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUser(email);

        String lockertype = String.valueOf(requestData.get("lockertype").toString());

        logger.info("Closing locker request for user: {}", email);

        service.closeLockerRequest(user, lockertype);

        // Sending email
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = user.getEmail(); // Use the email from the user
        String subject = "Locker Closing Request Confirmation";
        String messageBody = "Dear customer,\n\n"
                + "Thank you for submitting the request to close the locker.\n\n"
                + "Locker Type: " + lockertype + "\n\n"
                + "Your request has been received and is being processed.\n\n"
                + "We will notify you once the closing process is completed.\n\n"
                + "Regards,\n"
                + "Bank";

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
            logger.error("Error sending the locker closing request confirmation email: {}", e.getMessage());
        }

        return "Your request for closing " + lockertype + " has been sent to the employee. A confirmation email has been sent.";
    }

    // ... Other endpoints ...

}
