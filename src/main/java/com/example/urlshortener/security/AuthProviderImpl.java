package com.example.urlshortener.security;

import com.example.urlshortener.entity.User;
import com.example.urlshortener.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Autowired
    public AuthProviderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        String password = authentication.getCredentials().toString();

        if (!password.equals(user.get().getPassword()))
            throw new BadCredentialsException("Bad credentials");

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(user, null, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
