package edu.mirea.vitality.blog.service.security.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService {
    UserDetailsService userDetailsService();
}
