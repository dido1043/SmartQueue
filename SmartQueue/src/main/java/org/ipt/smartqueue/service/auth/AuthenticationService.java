package org.ipt.smartqueue.service.auth;

import lombok.AllArgsConstructor;
import org.ipt.smartqueue.data.dto.UserDto;
import org.ipt.smartqueue.data.dto.auth.LoginRequestDto;
import org.ipt.smartqueue.data.enums.AuthProvider;
import org.ipt.smartqueue.data.model.User;
import org.ipt.smartqueue.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserDto registerUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(AuthProvider.EMAIL);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public UserDto loginUser(LoginRequestDto requestDto) {
        String password = requestDto.getPassword();

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), password)
        );
        return modelMapper.map(userRepository.findByEmail(requestDto.getEmail()), UserDto.class);
    }

    public UserDto getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
