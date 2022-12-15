package ru.ccfit.filedrop.service.interfaces;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ccfit.filedrop.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto findByUsername(String username);
    UserDto getUserById(long id);
    List<UserDto> getAllUsers();
    void addUser(UserDto user);
    void deleteUser(UserDto user);
    void updateUser(UserDto user);
}
