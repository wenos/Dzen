package edu.mirea.myinvest.service.security;

import edu.mirea.myinvest.domain.model.Role;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.service.UserService;
import edu.mirea.myinvest.domain.dto.auth.JwtAuthenticationResponse;
import edu.mirea.myinvest.domain.dto.auth.SignInRequest;
import edu.mirea.myinvest.domain.dto.auth.SignUpRequest;
import edu.mirea.myinvest.domain.model.system.SystemPropertyKey;
import edu.mirea.myinvest.exception.system.RegistrationTemporarilyUnavailableProblem;
import edu.mirea.myinvest.exception.user.InvalidUserDataProblem;
import edu.mirea.myinvest.repository.UserRepository;
import edu.mirea.myinvest.service.security.interfaces.AuthenticationService;
import edu.mirea.myinvest.service.security.interfaces.JwtService;
import edu.mirea.myinvest.service.system.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfigService configService;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        if (!configService.getProperty(SystemPropertyKey.SYSTEM_REGISTRATION_ENABLED).getBooleanValue()) {
            throw new RegistrationTemporarilyUnavailableProblem();
        }

        var user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        var user = userRepository
                .findByUsername(request.username())
                .orElseThrow(InvalidUserDataProblem::new);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
