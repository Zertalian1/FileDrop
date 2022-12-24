package ru.ccfit.filedrop.service.interfaces;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.exception.NotUniqueUserException;

import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * используется для spring security
     * @param username - имя пользователя
     * @return найденный пользователь
     * @throws UsernameNotFoundException, если пользователя не существует
     */
    UserDetails loadUserByUsername(String username);
    /**
     *
     * @param username - имя пользователя
     * @return найденный пользователь
     * @throws NotFoundException, если пользователя не существует
     */
    UserDto findByUsername(String username);

    /**
     *
     * @param id - id пользователя в системе
     * @return найденный пользователь
     * @throws NotFoundException, если пользователя не существует
     */
    UserDto getUserById(long id);

    /**
     *
     * @return список всех пользователей
     */
    List<UserDto> getAllUsers();

    /**
     *
     * @param user - новый пользователь
     * @throws NotUniqueUserException, если пользователь с таким именем уже существует
     */
    void addUser(UserDto user);

    /**
     *
     * @param user - пользователь, которого необходимо удалить
     */
    void deleteUser(UserDto user);

    /**
     *
     * @param user - пользователь с новыми данными
     * @throws NotFoundException, если пользователя не существует
     */
    void updateUser(UserDto user);
}
