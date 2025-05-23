package com.matchnplay.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.matchnplay.repository.UserRepository;
import com.matchnplay.model.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final OtpService otpService;

    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    @Async
    public void sendVerificationToken(UUID userId, String email) {
        final var token = otpService.generateAndStoreOtp(userId);

        // Localhost URL with userId and OTP token
        final var emailVerificationUrl = "http://localhost:8080/api/auth/email/verify?uid=%s&t=%s"
                .formatted(userId, token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(GONE, "User account has been deleted or deactivated"));

        final var emailText = String.format(
                """
                        Dear %s,

                        Thank you for registering with MatchNPlay! We're thrilled to have you join our community. To complete your registration and verify your email address, please click the link below:

                        %s

                        If you did not create an account with us, please ignore this email. For any questions or assistance, feel free to reach out to our support team.

                        Warm regards,
                        The MatchNPlay Team
                        """,
                user.getUsername(), emailVerificationUrl);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setFrom("System");
        message.setText(emailText);

        mailSender.send(message);
    }

    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmail(email)
                .filter(u -> !u.isEmailVerified())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Email not found or already verified"));

        sendVerificationToken(user.getId(), user.getEmail());
    }

    @Transactional
    public User verifyEmail(UUID userId, String token) {
        if (!otpService.isOtpValid(userId, token)) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "Token invalid or expired");
        }
        otpService.deleteOtp(userId);

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(GONE,
                        "User account has been deleted or deactivated"));

        if (user.isEmailVerified()) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "Email is already verified");
        }

        user.setEmailVerified(true);

        return user;
    }

}