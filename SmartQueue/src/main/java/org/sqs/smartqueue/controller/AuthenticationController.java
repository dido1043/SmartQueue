package org.sqs.smartqueue.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sqs.smartqueue.data.dto.UserDto;
import org.sqs.smartqueue.data.dto.auth.LoginRequestDto;
import org.sqs.smartqueue.data.dto.auth.LoginResponse;
import org.sqs.smartqueue.data.model.User;
import org.sqs.smartqueue.service.auth.AuthenticationService;
import org.sqs.smartqueue.service.auth.AuthorizationService;
import org.sqs.smartqueue.service.auth.JwtService;
import org.sqs.smartqueue.service.auth.OAuthCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Value("${app.security.cookie-secure:true}")
    private boolean cookieSecure;

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final OAuthCodeService oAuthCodeService;
    private final ModelMapper modelMapper;
    private final AuthorizationService authorizationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.registerUser(userDto));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequestDto,
                                               HttpServletResponse response) {
        User authenticatedUser = modelMapper.map(authenticationService.loginUser(loginRequestDto), User.class);

        String jwtToken = jwtService.generateToken((UserDetails) authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRole(authenticatedUser.getRole().toString());
        loginResponse.setUserId(authenticatedUser.getId());

        addAuthCookie(response, jwtToken);
        return ResponseEntity.ok(loginResponse);
    }

    private void addAuthCookie(HttpServletResponse response, String jwtToken) {
        ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMillis(jwtService.getExpirationTime()))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
