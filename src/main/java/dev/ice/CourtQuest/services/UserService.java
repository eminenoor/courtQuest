package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.User;
import dev.ice.CourtQuest.repos.UserRepository;
import dev.ice.CourtQuest.util.EmailUtil;
import dev.ice.CourtQuest.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;

    @Autowired // May change
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User foundUser = user.get();
            foundUser.setFirstName(newUser.getFirstName());
            foundUser.setLastName(newUser.getLastName());
            foundUser.setEmail(newUser.getEmail());
            foundUser.setPassword(newUser.getPassword());
            //foundUser.setActivities(newUser.getActivities());
            foundUser.setAge(newUser.getAge());
            foundUser.setBirthDate(newUser.getBirthDate());
            //foundUser.setCreatedActivities(newUser.getCreatedActivities());
            foundUser.setDepartment(newUser.getDepartment());
            foundUser.setRating(newUser.getRating());
            return userRepository.save(foundUser);
        }
        else{
            return null;
        }
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
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
        User user = userRepository.findByEmail(email);
        if (otp1.equals(otp2)){
            // register
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

}
