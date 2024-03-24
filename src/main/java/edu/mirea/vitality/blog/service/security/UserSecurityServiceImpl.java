package edu.mirea.vitality.blog.service.security;

import edu.mirea.vitality.blog.service.security.interfaces.UserSecurityService;
import edu.mirea.vitality.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository repository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
