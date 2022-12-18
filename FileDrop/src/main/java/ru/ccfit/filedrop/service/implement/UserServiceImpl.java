package ru.ccfit.filedrop.service.implement;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ccfit.filedrop.details.UserDetailsImpl;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.entity.User;
import ru.ccfit.filedrop.enumeration.Role;
import ru.ccfit.filedrop.exception.NotUniqueUserException;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.mapper.UserMapper;
import ru.ccfit.filedrop.repository.UserRepository;
import ru.ccfit.filedrop.service.interfaces.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final   UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByName(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(()->new UsernameNotFoundException("Пользователь " + username + " не найден!"));
    }

    @Override
    public UserDto findByUsername(String username) {
        return userRepository
                .findByName(username)
                .map(userMapper::userToUserDto)
                .orElseThrow(()->new NotFoundException("Пользователь " + username + " не найден!"));
    }

    @Override
    public UserDto getUserById(long id) {
        return userRepository
                .findById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + id + " не найден!"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream().map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public void addUser(UserDto user) {
      User newUser = userMapper.userDtoToUser(user);
      User oldUser = userRepository.findByName(user.getName()).orElse(null);

      if (oldUser != null) {
        throw new NotUniqueUserException();
      }

      newUser.setRole(Role.USER);
      newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
      userRepository.save(newUser);
    }

    @Override
    public void deleteUser(UserDto user) {
      userRepository.deleteById(user.getId());
    }

    @Override
    public void updateUser(UserDto user) {
        Optional<User> oldUser = userRepository.findById(user.getId());

        oldUser.ifPresentOrElse(
                client -> userRepository.save(
                        UserMapper.updateUserByNotNullFieldsOfUserDto(client, user)),
                () -> {
                    throw new NotFoundException("Пользователь с id: " + user.getId() + " не найден!");
                }
        );
    }


}
