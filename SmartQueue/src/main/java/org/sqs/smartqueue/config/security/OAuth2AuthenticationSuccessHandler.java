package org.sqs.smartqueue.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sqs.smartqueue.data.dto.auth.LoginResponse;
import org.sqs.smartqueue.data.enums.AuthProvider;
import org.sqs.smartqueue.data.enums.RoleEnum;
import org.sqs.smartqueue.data.model.User;
import org.sqs.smartqueue.repository.UserRepository;
import org.sqs.smartqueue.service.auth.JwtService;
import org.sqs.smartqueue.service.auth.OAuthCodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OAuthCodeService oAuthCodeService;

    @Value("${frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        User user = resolveUser(token);

       String jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), "", Collections.emptyList()));
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRole(user.getRole().toString());
        loginResponse.setUserId(user.getId());

        String exchangeCode = oAuthCodeService.createCode(loginResponse);
        String base = frontendUrl.endsWith("/") ? frontendUrl.substring(0, frontendUrl.length() - 1) : frontendUrl;
        String redirectUrl = base + "/oauth/callback?code=" + URLEncoder.encode(exchangeCode, StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }

    private User resolveUser(OAuth2AuthenticationToken token) {
        OAuth2User oAuth2User = token.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User u = new User();
                    u.setName(name);
                    u.setEmail(email);
                    u.setProvider(AuthProvider.GOOGLE);
                    u.setRole(RoleEnum.CLIENT);
                    return userRepository.save(u);
                });
    }
}