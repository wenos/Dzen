package edu.mirea.vitality.blog.service.security.interfaces;


import edu.mirea.vitality.blog.domain.dto.auth.JwtAuthenticationResponse;
import edu.mirea.vitality.blog.domain.dto.auth.SignInRequest;
import edu.mirea.vitality.blog.domain.dto.auth.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
