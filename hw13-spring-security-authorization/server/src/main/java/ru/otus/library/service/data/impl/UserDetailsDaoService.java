package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.User;
import ru.otus.library.repository.UserRepository;
import ru.otus.library.service.data.UserService;

import java.util.List;

@Service
public class UserDetailsDaoService implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
