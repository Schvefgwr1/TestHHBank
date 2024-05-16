package bank.testhhbank.web.api;

import bank.testhhbank.models.User;
import bank.testhhbank.web.DTO.FullInformationRequest;
import bank.testhhbank.web.DTO.UserResponse;
import bank.testhhbank.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path="/api/users", produces="application/json")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{user_id}")
    public User getUser(@PathVariable("user_id") Long user_id) {
        return userService.findUserById(user_id);
    }

    @PatchMapping(path="/full_inf/{user_id}")
    public ResponseEntity<UserResponse> setFullInformation(
            @PathVariable("user_id") Long user_id,
            @RequestBody FullInformationRequest fullInformationRequest
    ) {
        UserResponse response = userService.addFullInformation(
                user_id,
                fullInformationRequest.getFirstname(),
                fullInformationRequest.getLastname(),
                fullInformationRequest.getSurname(),
                fullInformationRequest.getDateOfBirth()
        );
        if(Objects.equals(response.getMessage(), "Successful patch")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/new_contacts/{user_id}")
    public ResponseEntity<UserResponse> setNewContacts(
            @PathVariable("user_id") Long user_id,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email
    ) {
        UserResponse response = userService.addNewContacts(
                user_id,
                phoneNumber,
                email
        );
        if(Objects.equals(response.getMessage(), "Successful patch")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/delete_email/{user_id}")
    public ResponseEntity<UserResponse> deleteEmail(
            @PathVariable("user_id") Long user_id
    ) {
        UserResponse response = userService.deleteEmail(user_id);
        if(Objects.equals(response.getMessage(), "Successful delete")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path="/delete_phone/{user_id}")
    public ResponseEntity<UserResponse> deletePhoneNumber(
            @PathVariable("user_id") Long user_id
    ) {
        UserResponse response = userService.deletePhoneNumber(user_id);
        if(Objects.equals(response.getMessage(), "Successful delete")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
