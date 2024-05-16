package bank.testhhbank.web.services;

import bank.testhhbank.data.UserRepository;
import bank.testhhbank.models.User;

import bank.testhhbank.web.DTO.FilterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final UserRepository userRepository;

    public FilterResponse findUsersByParameter(String param) {
        try {
            LocalDate date = LocalDate.parse(param);
            List<User> users = userRepository.findUsersByDateOfBirthAfter(date)
                    .stream()
                    .peek(user -> user.setPassword("No information"))
                    .collect(Collectors.toList());
            return FilterResponse.builder()
                    .users(users)
                    .message("Success find by date of birth")
                    .type("By date of birthday")
                    .build();
        } catch (DateTimeParseException e) {
            if(EmailValidator.getInstance().isValid(param)) {
                Optional<User> userCont = userRepository.findByEmail(param);
                if(userCont.isPresent()) {
                    userCont.get().setPassword("No information");
                    return FilterResponse.builder()
                            .users(List.of(userCont.get()))
                            .message("Success find by Email")
                            .type("By email")
                            .build();
                }
                else {
                    return FilterResponse.builder()
                            .message("No users find by Email")
                            .type("By email")
                            .build();
                }
            }
            else {
                if(isValidPhoneNumber(param)) {
                    Optional<User> userCont = userRepository.findByPhoneNumber(param);
                    if(userCont.isPresent()) {
                        userCont.get().setPassword("No information");
                        return FilterResponse.builder()
                                .users(List.of(userCont.get()))
                                .message("Success find by Number")
                                .type("By phone number")
                                .build();
                    }
                    else {
                        return FilterResponse.builder()
                                .message("No users find by Number")
                                .type("By phone number")
                                .build();
                    }
                }
                else {
                    if(isValidFIO(param)) {
                        String[] args = param.split(" ");
                        List<User> users = userRepository.findUsersByFIO(
                                args[0],
                                args[1],
                                args[2]
                        );
                        return FilterResponse.builder()
                                .users(users)
                                .message("Success find by FIO")
                                .type("By FIO")
                                .build();
                    }
                    else {
                        return FilterResponse.builder()
                                .message("Incorrect path parameter")
                                .type("no type")
                                .build();
                    }
                }
            }
        }
    }

    private boolean isValidPhoneNumber(String input) {
        String regex = "^\\+7 \\(9\\d{2}\\) \\d{3}-\\d{2}-\\d{2}$";
        return input.matches(regex);
    }

    private boolean isValidFIO(String input) {
        String regex = "^(?:[A-Z][a-z]* ){2}[A-Z][a-z]*$";
        return input.matches(regex);
    }

}
