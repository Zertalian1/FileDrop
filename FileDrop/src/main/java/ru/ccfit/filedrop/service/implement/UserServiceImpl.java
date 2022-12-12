package ru.ccfit.filedrop.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ccfit.filedrop.details.UserDetailsImpl;
import ru.ccfit.filedrop.repository.UserRepository;
import ru.ccfit.filedrop.service.interfaces.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByName(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

}
