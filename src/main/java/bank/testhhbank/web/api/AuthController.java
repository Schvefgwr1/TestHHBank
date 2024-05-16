package bank.testhhbank.web.api;


import bank.testhhbank.models.User;
import bank.testhhbank.security.models.input.RefreshTokenRequest;
import bank.testhhbank.security.models.input.SignInRequest;
import bank.testhhbank.security.models.output.RefreshTokenResponse;
import bank.testhhbank.security.models.output.SignInResponse;
import bank.testhhbank.security.services.AuthService;
import bank.testhhbank.web.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json", path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        String saveInformation = userService.saveUser(user);
        HttpStatus httpStatus = switch (saveInformation) {
            case "Incorrect part of data" -> HttpStatus.BAD_REQUEST;
            case "User is already exist" -> HttpStatus.CONFLICT;
            case "Success saving" -> HttpStatus.CREATED;
            default -> HttpStatus.OK;
        };
        return new ResponseEntity<>(user, httpStatus);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        SignInResponse response = authService.signIn(signInRequest);
        HttpStatus httpStatus;
        if(response.getStatus() == 200) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshTokenResponse response = authService.refreshToken(refreshTokenRequest);
        HttpStatus httpStatus;
        if(response.getStatus() == 200) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, httpStatus);
    }
}
