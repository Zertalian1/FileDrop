package ru.ccfit.filedrop.service.implement;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ccfit.filedrop.details.UsersUserDetails;
import ru.ccfit.filedrop.repository.UserRepository;
import ru.ccfit.filedrop.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(UsersUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

}
