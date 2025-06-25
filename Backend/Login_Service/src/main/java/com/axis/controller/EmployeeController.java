package com.axis.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Account;
import com.axis.entity.AuthRequest;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;
import com.axis.util.JwtUtil;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/login/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl service;

    @Autowired
    private JavaMailSender javaMailSender;

    // Map to store the OTP and its expiration time (in milliseconds) for each vendor
    private static final Map<String, String> otpMap = new ConcurrentHashMap<>();
    private static final int OTP_EXPIRATION_DURATION = 5 * 60 * 1000; // 5 minutes

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        return "Hello " + username + "(employee) welcome home.\nYou're logged in.";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        String username = authRequest.getUsername();

        Users vendor = service.getUserByUsername(username);

        if (vendor != null) {
            if (vendor.isBlocked()) {
                logger.warn("Employee account is blocked. Username: {}", username);
                throw new Exception("Your account is blocked. Please check your email for the unblock link.");
            }

            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
                // Reset login attempts on successful login
                vendor.setLoginAttempts(0);
                service.updateEmployee(vendor);
                logger.info("Employee login successful. Username: {}", username);
            } catch (Exception ex) {
                // Increase login attempts and block the account after three unsuccessful attempts
                vendor.setLoginAttempts(vendor.getLoginAttempts() + 1);
                if (vendor.getLoginAttempts() >= 3) {
                    vendor.setBlocked(true);
                    // Send email with unblock link
                    sendEmailWithUnblockLink(vendor);
                    logger.warn("Employee account blocked due to consecutive unsuccessful login attempts. Username: {}", username);
                }
                service.updateEmployee(vendor);
                logger.warn("Employee login failed. Invalid username/password. Username: {}, Attempts: {}", username, vendor.getLoginAttempts());
                throw new Exception("Invalid username/password. Attempts: " + vendor.getLoginAttempts());
            }

            return ResponseEntity.ok(jwtUtil.generateToken(username));
        } else {
            logger.warn("Employee login failed. Employee not found. Username: {}", username);
            throw new Exception("Employee with username " + username + " not found.");
        }
    }

    @GetMapping("/unblock")
    public ResponseEntity<?> unblockAccount(@RequestParam String username) {
        Users vendor = service.getUserByUsername(username);

        if (vendor != null && vendor.isBlocked()) {
            vendor.setBlocked(false);
            vendor.setLoginAttempts(0);
            service.updateEmployee(vendor);

            logger.info("Employee account unblocked successfully. Username: {}", username);
            return ResponseEntity.ok("Your account has been unblocked successfully.");
        } else {
            logger.warn("Invalid request to unblock the account. Username: {}", username);
            return ResponseEntity.badRequest().body("Invalid request to unblock the account.");
        }
    }

    private void sendEmailWithUnblockLink(Users vendor) {
        // Email content
        String subject = "Account Blocked - Unblock Link";
        String message = "Hello " + vendor.getUsername() + ",\n\nYour account has been blocked due to three consecutive unsuccessful login attempts.\n"
                + "To unblock your account, click on the following link:\n"
                + "http://localhost:8081/employee/unblock?username=" + vendor.getUsername() + "\n\n"
                + "If you did not attempt these logins, please contact our support team.\n\n"
                + "Best regards,\nYour Vendor Team";

        // Send the email
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(vendor.getEmail());
        email.setSubject(subject);
        email.setText(message);
        javaMailSender.send(email);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendForgotPasswordOTP(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String username = forgotPasswordRequest.getUsername();
        Users vendor = service.findUserByEmail(username);

        if (vendor == null) {
            logger.warn("Employee forgot password failed. Employee not found. Username: {}", username);
            return ResponseEntity.notFound().build();
        }

        // Generate a random 6-digit OTP
        String otp = generateOTP();

        // Store the OTP and its expiration time
        long expirationTime = System.currentTimeMillis() + OTP_EXPIRATION_DURATION;
        otpMap.put(username, otp + ":" + expirationTime);

        // Send the OTP to the user's email
        String recipientEmail = vendor.getEmail();
        String subject = "Password Reset OTP";
        String messageBody = "Your OTP to reset the password is: " + otp;

        sendEmail(recipientEmail, subject, messageBody);

        logger.info("Employee forgot password - OTP sent successfully. Username: {}", username);
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String username = resetPasswordRequest.getUsername();
        String otp = resetPasswordRequest.getOtp();
        String newPassword = resetPasswordRequest.getNewPassword();

        // Retrieve the stored OTP and its expiration time
        String storedOtpAndExpiration = otpMap.get(username);
        if (storedOtpAndExpiration == null) {
            logger.warn("Employee reset password failed. OTP not found. Username: {}", username);
            return ResponseEntity.badRequest().body("OTP not found. Please request an OTP again.");
        }

        String[] otpAndExpiration = storedOtpAndExpiration.split(":");
        String storedOtp = otpAndExpiration[0];
        long expirationTime = Long.parseLong(otpAndExpiration[1]);

        // Check if the OTP is expired
        if (System.currentTimeMillis() > expirationTime) {
            otpMap.remove(username);
            logger.warn("Employee reset password failed. OTP has expired. Username: {}", username);
            return ResponseEntity.badRequest().body("OTP has expired. Please request an OTP again.");
        }

        // Check if the provided OTP matches the stored OTP
        if (!otp.equals(storedOtp)) {
            logger.warn("Employee reset password failed. Invalid OTP. Username: {}", username);
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        // Reset the password for the user
        Users vendor = service.findUserByEmail(username);
        vendor.setPassword(newPassword);
        service.editVendor(vendor);

        // Password reset successful, remove the OTP entry from the map
        otpMap.remove(username);

        // Notify the user that the password has been reset
        String recipientEmail = vendor.getEmail();
        String subject = "Password Reset Successful";
        String messageBody = "Your password has been successfully reset.";

        sendEmail(recipientEmail, subject, messageBody);

        logger.info("Employee reset password successful. Username: {}", username);
        return ResponseEntity.ok("Password reset successful.");
    }

    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        return String.valueOf(otpNumber);
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        Users user = service.viewProfile(username);
        return user.toString();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Users> editUserDetails(@PathVariable int id, @RequestBody Users updatedUser) {
        // Check if the user with the given id exists
        Users existingUser = service.findUserById(id);
        if (existingUser == null) {
            logger.warn("Edit user details failed. User not found. UserID: {}", id);
            return ResponseEntity.notFound().build();
        }

        // Set the id from the path variable to the updatedUser
        updatedUser.setId(id);

        Users user = service.editUser(updatedUser);
        if (user != null) {
            logger.info("Edit user details successful. UserID: {}", id);
            return ResponseEntity.ok(user);
        } else {
            logger.warn("Edit user details failed. UserID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pending-accounts")
    public List<Account> viewAllPendingAccounts() {
        return service.viewAllPendingAccounts();
    }

    @PutMapping("/activate-account")
    public String activateAccount(@RequestBody Map<String, Object> requestData) {
        int accountId = Integer.valueOf(requestData.get("accid").toString());

        service.activateAccount(accountId);
        Account account = service.findAccountById(accountId);
        Users user = service.findUserById(account.getUserid());

        // Check if the user exists
        if (user == null) {
            logger.warn("Account activation failed. User not found. AccountID: {}", accountId);
            return "Account activation failed. User not found.";
        }

        // Sending email
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = user.getEmail(); // Replace with the recipient's email address
        String subject = "Account Activation Confirmation";
        String messageBody = "Dear Customer,\n\n"
                + "We are pleased to inform you that your account has been successfully activated.\n\n"
                // + "Account Number: " + account.getAccountNumber() + "\n\n"
                + "Thank you for choosing Bank.\n\n"
                + "Best Regards,\n"
                + " Bank";

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
            logger.info("Locker activation email sent successfully. AccountID: {}", accountId);
            return "Account is activated successfully. Confirmation email has been sent.";
        } catch (MessagingException e) {
            logger.warn("Locker activation email sending failed. AccountID: {}", accountId);
            return "Account is activated successfully, but there was an error sending the confirmation email.";
        }
    }

    @GetMapping("/all-accounts")
    public List<Account> viewAllAccounts() {
        return service.listAllAccounts();
    }

    @GetMapping("/all-customers")
    public List<Users> allCustomers() {
        return service.listAllCustomers();
    }

    @DeleteMapping("/delete-customer")
    public String deleteCustomer(@RequestBody Map<String, Object> requestData) {
        String accno = String.valueOf(requestData.get("accno").toString());
        service.deleteUserAndAccount(accno);
        logger.info("Customer deleted successfully. AccountNumber: {}", accno);
        return "Customer is deleted successfully";
    }

    private void sendEmail(String recipientEmail, String subject, String messageBody) {
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual sender email password

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
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
            logger.info("Email sent successfully. RecipientEmail: {}", recipientEmail);
        } catch (MessagingException e) {
            logger.warn("Email sending failed. RecipientEmail: {}", recipientEmail);
            e.printStackTrace();
        }
    }
}
