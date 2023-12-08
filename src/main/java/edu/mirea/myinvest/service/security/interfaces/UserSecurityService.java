package edu.mirea.myinvest.service.security.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService {
    UserDetailsService userDetailsService();
}
