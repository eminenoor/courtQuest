package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Rating;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.RatingRepository;
import dev.ice.CourtQuest.repos.UserRepository;
import dev.ice.CourtQuest.util.EmailUtil;
import dev.ice.CourtQuest.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final RatingRepository ratingRepository;

    @Autowired
    public UserService(UserRepository userRepository, OtpUtil otpUtil, EmailUtil emailUtil, JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.ratingRepository = ratingRepository;
    }

    public List<UserDB> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDB saveUser(UserDB user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the user first to ensure it gets an ID
        UserDB savedUser = userRepository.save(user);

        // Create and save the Rating
        Rating rating = new Rating();
        rating.setRatedUser(savedUser); // Associate the rating with the saved user
        savedUser.setReceivedRatings(rating);
        ratingRepository.save(rating);

        // Set the saved rating to the user

        // Save the user again to update the rating association
        return savedUser;
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
            foundUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            foundUser.setAge(newUser.getAge());
            foundUser.setBirth_date(newUser.getBirth_date());
            foundUser.setDepartment(newUser.getDepartment());
            foundUser.setRating(newUser.getRating());
            foundUser.setAvatar(newUser.getAvatar());
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
        try {
            UserDB user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDB.class), username);
            if(user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            return User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .build();
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserDB findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDB getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userRepository.findByEmailWithActivities(username);
        return currentUser;
    }


    public List<UserDB> getAllExceptParticipants(Activity activity){
        return null;
    }

    public UserDB editUser(Long user_id, UserDB newUser) {
        Optional<UserDB> user = userRepository.findById(user_id);
        if(user.isPresent()) {
            UserDB foundUser = user.get();
            foundUser.setFirst_name(newUser.getFirst_name());
            foundUser.setLast_name(newUser.getLast_name());
            foundUser.setAge(newUser.getAge());
            foundUser.setReceivedRatings(newUser.getReceivedRatings());
            foundUser.setBirth_date(newUser.getBirth_date());
            foundUser.setDepartment(newUser.getDepartment());
            foundUser.setAvatar(newUser.getAvatar());
            return userRepository.save(foundUser);
        } else {
            return null;
        }
    }
}