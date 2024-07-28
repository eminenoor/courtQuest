package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.UserRepository;
import dev.ice.CourtQuest.util.EmailUtil;
import dev.ice.CourtQuest.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(UserRepository userRepository, OtpUtil otpUtil, EmailUtil emailUtil, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserDB> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDB saveUser(UserDB user) {
        return userRepository.save(user);
    }

    public UserDB getUser(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    public UserDB updateUser(Long user_id, UserDB newUser) {
        Optional<UserDB> user = userRepository.findById(user_id);
        if(user.isPresent()) {
            UserDB foundUser = user.get();
            foundUser.setFirst_name(newUser.getFirst_name());
            foundUser.setLast_name(newUser.getLast_name());
            foundUser.setEmail(newUser.getEmail());
            foundUser.setPassword(newUser.getPassword()); // Use {noop} prefix
            foundUser.setAge(newUser.getAge());
            foundUser.setBirth_date(newUser.getBirth_date());
            foundUser.setDepartment(newUser.getDepartment());
            foundUser.setRating(newUser.getRating());
            return userRepository.save(foundUser);
        } else {
            return null;
        }
    }

    public void deleteById(Long user_id) {
        userRepository.deleteById(user_id);
    }

    public String getOtp(String email) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        return otp;
    }

    public String verifyOtp(String email, String otp1, String otp2) {
        UserDB user = userRepository.findByEmail(email);
        if (otp1.equals(otp2)){
            // register
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT * FROM user where email = ?";
        UserDB user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDB.class), username);
        if(user == null) {
            throw new RuntimeException("User does not exist");
        }

        return User.withUsername(user.getEmail())
                .password("{noop}" + user.getPassword()) // Use {noop} prefix
                .build();
    }

}
