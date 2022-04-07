package com.security.authentication_server;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    public void addUser(User user) {
        User u = new User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getRole());
        userRepository.save(u);
    }

    public void auth(User user) {
        Optional<User> o = userRepository.findUserByUsername(user.getUsername());

        if (o.isPresent()) {
            User u = o.get();
            if (passwordEncoder.matches(
                    user.getPassword(),
                    u.getPassword())) {
                renewOpt(u);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            return otpToValidate.getCode().equals(otp.getCode());
        }
        return false;
    }

    public String getRole(String username) {
        Optional<User> o = userRepository.findUserByUsername(username);
        if (o.isPresent()){
            return o.get().getRole();
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    private void renewOpt(User u) {
        String code = GenerateCodeUtil.generateCode();

        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            Otp o = new Otp(otp.getUsername(), code);
            otpRepository.save(o);
        } else {
            otpRepository.save(new Otp(u.getUsername(), code));
        }
    }
}
