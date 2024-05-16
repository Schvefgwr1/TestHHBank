package bank.testhhbank.security.services;


import bank.testhhbank.models.User;
import bank.testhhbank.security.models.input.RefreshTokenRequest;
import bank.testhhbank.security.models.input.SignInRequest;
import bank.testhhbank.security.models.output.RefreshTokenResponse;
import bank.testhhbank.security.models.output.SignInResponse;
import bank.testhhbank.web.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UsersDetailsService usersDetailsService;
    private final UserService userService;

    public SignInResponse signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
            User user = (User) usersDetailsService.loadUserByUsername(request.getUsername());
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            log.info("Successful sign in of user {}", user.getLogin());
            return SignInResponse
                    .builder()
                    .status(200)
                    .token(jwt)
                    .refreshToken(refreshToken)
                    .message("Successfully signed in")
                    .build()
            ;
        } catch (Exception e){
            log.error(e.getMessage());
            return SignInResponse
                    .builder()
                    .status(400)
                    .message(e.getMessage())
                    .build()
            ;
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String ourUsername = jwtUtils.extractUsername(refreshTokenRequest.getRefreshToken());
        if (Objects.equals(ourUsername, refreshTokenRequest.getUsername())) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    ourUsername, refreshTokenRequest.getPassword()
            ));
            User user = (User) usersDetailsService.loadUserByUsername(refreshTokenRequest.getUsername());
            if (jwtUtils.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {
                var jwt = jwtUtils.generateToken(user);
                log.info("Successful refresh token of user {}", user.getLogin());
                return RefreshTokenResponse
                        .builder()
                        .status(200)
                        .token(jwt)
                        .refreshToken(refreshTokenRequest.getRefreshToken())
                        .message("Successfully refreshed token")
                        .build()
                        ;
            }
        }
        log.error("Invalid credential for {}", ourUsername);
        return RefreshTokenResponse
                .builder()
                .status(400)
                .username(ourUsername)
                .build()
        ;
    }
}