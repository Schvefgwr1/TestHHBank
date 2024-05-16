package bank.testhhbank.web.services;

import bank.testhhbank.data.UserRepository;
import bank.testhhbank.models.User;

import bank.testhhbank.web.DTO.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private Validator validator;

    public Iterable<User> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .peek(user -> user.setPassword("No information"))
                .collect(Collectors.toList());
    }

    public User findUserById(Long id) {
        Optional<User> userCont = userRepository.findById(id);
        if(userCont.isEmpty()) {
            return new User();
        }
        else {
            User user = userCont.get();
            user.setPassword("No information");
            return user;
        }
    }

    public String saveUser(User user) {
        if((user.getEmail() != null) && (user.getPhoneNumber() != null)) {
            Optional<User> existUser1 = userRepository.findByParam(
                    user.getEmail(),
                    user.getLogin(),
                    user.getPhoneNumber()
            );
            if(existUser1.isEmpty()) {
                String password = user.getPassword();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setPrice(user.getInitialPrice());
                User saveUser = userRepository.save(user);
                user.setId(saveUser.getId());
                user.setPassword(password);
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

    public UserResponse addFullInformation(
            Long user_id,
            String firstname,
            String lastname,
            String surname,
            String dateOfBirth
    ) {
        Optional<User> userCont = userRepository.findById(user_id);
        if(userCont.isPresent()) {
            User user = userCont.get();
            if(user.getDateOfBirth() == null) {
                try {
                    LocalDate date = LocalDate.parse(dateOfBirth);
                    user.setDateOfBirth(date);
                }
                catch(DateTimeParseException e) {
                    return UserResponse.builder()
                            .user(user)
                            .message("Incorrect form of Date")
                            .build();
                }
            }
            if(user.getFirstname() == null) {
                user.setFirstname(firstname);
            }
            if(user.getLastname() == null) {
                user.setLastname(lastname);
            }
            if(user.getSurname() == null) {
                user.setSurname(surname);
            }
            User newUser = userRepository.save(user);
            newUser.setPassword("No information");
            return UserResponse.builder()
                    .user(user)
                    .message("Successful patch")
                    .build();
        }
        else {
            return UserResponse.builder()
                    .message("Incorrect number of user")
                    .build();
        }
    }

    public UserResponse addNewContacts(
            Long user_id,
            String phoneNumber,
            String email
    ) {

        Optional<User> userCont = userRepository.findById(user_id);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        if(userCont.isPresent()) {
            User user = userCont.get();
            if(phoneNumber != null) {
                Optional<User> findUserCont = userRepository.findByPhoneNumber(phoneNumber);
                if(findUserCont.isEmpty()) {
                    String oldPhoneNumber = user.getPhoneNumber();
                    user.setPhoneNumber(phoneNumber);
                    Set<ConstraintViolation<User>> violations = validator.validate(user);
                    if(!violations.isEmpty()) {
                        user.setPhoneNumber(oldPhoneNumber);
                        user.setPassword("No information");
                        return UserResponse.builder()
                                .user(user)
                                .message(violations.toString())
                                .build();
                    }
                }
                else {
                    user.setPassword("No information");
                    return UserResponse.builder()
                            .user(user)
                            .message("Phone number already exist")
                            .build();
                }
            }
            if(email != null) {
                Optional<User> findUserCont = userRepository.findByEmail(email);
                if(findUserCont.isEmpty()) {
                    String oldEmail = user.getEmail();
                    user.setEmail(email);
                    Set<ConstraintViolation<User>> violations = validator.validate(user);
                    if(!violations.isEmpty()) {
                        user.setEmail(oldEmail);
                        user.setPassword("No information");
                        return UserResponse.builder()
                                .user(user)
                                .message(violations.toString())
                                .build();
                    }
                }
                else {
                    user.setPassword("No information");
                    return UserResponse.builder()
                            .user(user)
                            .message("Email already exist")
                            .build();
                }
            }
            User newUser = userRepository.save(user);
            newUser.setPassword("No information");
            return UserResponse.builder()
                    .user(user)
                    .message("Successful patch")
                    .build();

        }
        else {
            return UserResponse.builder()
                    .message("Incorrect number of user")
                    .build();
        }
    }

    public UserResponse deleteEmail(Long user_id) {
        Optional<User> userCont = userRepository.findById(user_id);

        if(userCont.isPresent()) {
            User user = userCont.get();
            if(user.getEmail() == null) {
                user.setPassword("No information");
                return UserResponse.builder()
                        .user(user)
                        .message("Email is already delete")
                        .build();
            }
            else {
                if(user.getPhoneNumber() == null) {
                    user.setPassword("No information");
                    return UserResponse.builder()
                            .user(user)
                            .message("You can`t delete all contacts")
                            .build();
                }
                else {
                    user.setEmail(null);
                    User newUser = userRepository.save(user);
                    newUser.setPassword("No information");
                    return UserResponse.builder()
                            .user(newUser)
                            .message("Successful delete")
                            .build();
                }
            }

        }
        else {
            return UserResponse.builder()
                    .message("Incorrect number of user")
                    .build();
        }
    }

    public UserResponse deletePhoneNumber(Long user_id) {
        Optional<User> userCont = userRepository.findById(user_id);

        if(userCont.isPresent()) {
            User user = userCont.get();
            if(user.getPhoneNumber() == null) {
                user.setPassword("No information");
                return UserResponse.builder()
                        .user(user)
                        .message("Phone number is already delete")
                        .build();
            }
            else {
                if(user.getEmail() == null) {
                    user.setPassword("No information");
                    return UserResponse.builder()
                            .user(user)
                            .message("You can`t delete all contacts")
                            .build();
                }
                else {
                    user.setPhoneNumber(null);
                    User newUser = userRepository.save(user);
                    newUser.setPassword("No information");
                    return UserResponse.builder()
                            .user(newUser)
                            .message("Successful delete")
                            .build();
                }
            }

        }
        else {
            return UserResponse.builder()
                    .message("Incorrect number of user")
                    .build();
        }
    }

}
