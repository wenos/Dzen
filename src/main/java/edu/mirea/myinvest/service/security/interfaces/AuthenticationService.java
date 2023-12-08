package edu.mirea.myinvest.service.security.interfaces;


import edu.mirea.myinvest.domain.dto.auth.JwtAuthenticationResponse;
import edu.mirea.myinvest.domain.dto.auth.SignInRequest;
import edu.mirea.myinvest.domain.dto.auth.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
