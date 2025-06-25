package com.axis.controller;

import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
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
//@CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
@RequestMapping("/login/customer")
//@CrossOrigin(origins = "http://localhost:3000")

public class CustomerController {

    private static final Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

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
        LOGGER.info("Home accessed by user: " + username);
        return "Hello "+username+"(user) welcome home.\nYou're logged in.";
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        String username = authRequest.getUsername();
        LOGGER.info("Authentication request for user: " + username);

        Users vendor = service.getUserByUsername(username);

        if (vendor != null) {
            if (vendor.isBlocked()) {
                LOGGER.warning("Authentication failed. Account blocked for user: " + username);
                throw new Exception("Your account is blocked. Please check your email for the unblock link.");
            }

            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
                // Reset login attempts on successful login
                vendor.setLoginAttempts(0);
                service.updateCustomer(vendor);
            } catch (Exception ex) {
                // Increase login attempts and block the account after three unsuccessful attempts
                vendor.setLoginAttempts(vendor.getLoginAttempts() + 1);
                if (vendor.getLoginAttempts() >= 3) {
                    vendor.setBlocked(true);
                    // Send email with unblock link
                    sendEmailWithUnblockLink(vendor);
                }
                service.updateCustomer(vendor);

                LOGGER.warning("Authentication failed for user: " + username + ". Attempts: " + vendor.getLoginAttempts());
                throw new Exception("Invalid username/password. Attempts: " + vendor.getLoginAttempts());
            }

            LOGGER.info("User logged in successfully: " + username);
            return ResponseEntity.ok(jwtUtil.generateToken(username));
        } else {
            LOGGER.warning("Customer with username " + username + " not found.");
            throw new Exception("Cusotmer with username " + username + " not found.");
        }
    }

    @GetMapping("/unblock")
    public ResponseEntity<?> unblockAccount(@RequestParam String username) {
        LOGGER.info("Unblock request received for user: " + username);

        Users vendor = service.getUserByUsername(username);

        if (vendor != null && vendor.isBlocked()) {
            vendor.setBlocked(false);
            vendor.setLoginAttempts(0);
            service.updateCustomer(vendor);

            LOGGER.info("User account unblocked: " + username);
            return ResponseEntity.ok("Your account has been unblocked successfully.");
        } else {
            LOGGER.warning("Invalid request to unblock the account: " + username);
            return ResponseEntity.badRequest().body("Invalid request to unblock the account.");
        }
    }

    private void sendEmailWithUnblockLink(Users vendor) {
        // Email content
        String subject = "Account Blocked - Unblock Link";
        String message = "Hello " + vendor.getUsername() + ",\n\nYour account has been blocked due to three consecutive unsuccessful login attempts.\n"
                + "To unblock your account, click on the following link:\n"
                + "http://localhost:8081/customer/unblock?username=" + vendor.getUsername() + "\n\n"
                + "If you did not attempt these logins, please contact our support team.\n\n"
                + "Best regards,\nYour Vendor Team";

        // Send the email
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(vendor.getEmail());
        email.setSubject(subject);
        email.setText(message);
        javaMailSender.send(email);

        LOGGER.info("Account blocked email sent to: " + vendor.getEmail());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendForgotPasswordOTP(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String username = forgotPasswordRequest.getUsername();
        LOGGER.info("Forgot password request received for user: " + username);

        Users vendor = service.findUserByEmail(username);

        if (vendor == null) {
            LOGGER.warning("Customer not found for forgot password request: " + username);
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

        LOGGER.info("Forgot password OTP sent to: " + recipientEmail);
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
            LOGGER.warning("OTP not found for reset password request: " + username);
            return ResponseEntity.badRequest().body("OTP not found. Please request an OTP again.");
        }

        String[] otpAndExpiration = storedOtpAndExpiration.split(":");
        String storedOtp = otpAndExpiration[0];
        long expirationTime = Long.parseLong(otpAndExpiration[1]);

        // Check if the OTP is expired
        if (System.currentTimeMillis() > expirationTime) {
            otpMap.remove(username);
            LOGGER.warning("OTP expired for reset password request: " + username);
            return ResponseEntity.badRequest().body("OTP has expired. Please request an OTP again.");
        }

        // Check if the provided OTP matches the stored OTP
        if (!otp.equals(storedOtp)) {
            LOGGER.warning("Invalid OTP for reset password request: " + username);
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

        LOGGER.info("Password reset successful for user: " + username);
        return ResponseEntity.ok("Password reset successful.");
    }

    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otpNumber = 100000 + random.nextInt(900000);
        return String.valueOf(otpNumber);
    }
    
    @PostMapping("/register")
    public Users create(@RequestBody Users user) {
        Users newUser = service.createCustomer(user);

        // Sending email
        String senderEmail = "dummyahealthcare22@gmail.com";// Replace with the actual email
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = user.getEmail(); // Use the email from the newly registered user
        String subject = "Registration Confirmation";
        String messageBody = "Dear customer,\n\n"
                            + "Thank you for registering with  Bank.\n\n"
                            + "Your registration is successful.\n\n"
                            + "Regards,\n"
                            + " Bank";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            javax.mail.Transport.send(message);

            LOGGER.info("Registration email sent to: " + recipientEmail);
        } catch (MessagingException e) {
            // Handle the error if the confirmation email fails to send
            LOGGER.warning("Error sending registration email to: " + recipientEmail);
            e.printStackTrace();
        }

        LOGGER.info("Customer registered successfully: " + newUser.getUsername());
        return newUser;
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        String username=request.getAttribute("username").toString();
        Users user=service.viewProfile(username);

        LOGGER.info("Profile viewed for user: " + username);
        return user.toString();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Users> editUserDetails(@PathVariable int id, @RequestBody Users updatedUser) {
        // Set the id from the path variable to the updatedUser
        updatedUser.setId(id);

        Users user = service.editUser(updatedUser);
        if (user != null) {
            LOGGER.info("User details updated: " + user.getUsername());
            return ResponseEntity.ok(user);
        } else {
            LOGGER.warning("User not found for edit: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/account-details")
    public String accountDetails(HttpServletRequest request) {
        String username=request.getAttribute("username").toString();
        Users user=service.viewProfile(username);
        Account account=service.findAccountByUserId(user.getId());

        LOGGER.info("Account details viewed for user: " + username);
        return account.toString();
    }

    private void sendEmail(String recipientEmail, String subject, String messageBody) {
        String senderEmail = "dummyahealthcare22@gmail.com";//replace with the actual sender email
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

            LOGGER.info("Email sent to: " + recipientEmail);
        } catch (MessagingException e) {
            LOGGER.warning("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Other methods...

}
