package bank.testhhbank.web.services;

import bank.testhhbank.data.UserRepository;
import bank.testhhbank.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationService {

    private UserRepository userRepository;

    @Autowired
    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUser(User user) {
        if((user.getEmail() != null) && (user.getPhoneNumber() != null)) {
            Optional<User> existUser1 = userRepository.findByParam(
                    user.getEmail(),
                    user.getLogin(),
                    user.getPhoneNumber()
            );
            if(existUser1.isEmpty()) {
                user.setPassword(String.valueOf(user.getPassword().hashCode()));
                userRepository.save(user);
                return "Success saving";
            }
            else {
                return "User is already exist";
            }
        }
        else {
            return "Incorrect part of data";
        }
    }

}
