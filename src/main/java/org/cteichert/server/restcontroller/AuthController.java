package org.cteichert.server.restcontroller;

import lombok.AllArgsConstructor;
import org.cteichert.server.bean.User;
import org.cteichert.server.repository.UserRepository;
import org.cteichert.server.requests.AuthRequest;
import org.cteichert.server.responses.CreateUserResponse;
import org.cteichert.server.responses.LoginUserResponse;
import org.cteichert.server.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> register(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        Optional<User> userOptional = userRepository.getByUserName(authRequest.getUsername());

        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName(authRequest.getUsername());
        user.setPwd(passwordEncoder.encode(authRequest.getPassword()));

        User createdUser = userRepository.save(user);
        return ResponseEntity.ok(new CreateUserResponse(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@Valid @RequestBody AuthRequest authRequest) {
        String userName = authRequest.getUsername();
        String userPwd = authRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPwd));
        if (authentication.isAuthenticated()) {
            Optional<User> user = userRepository.getByUserName(userName);
            return ResponseEntity.ok(new LoginUserResponse(user.get(), jwtTokenProvider.generateToken(authentication)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
