package ru.ccfit.filedrop.mapper;

import org.mapstruct.Mapper;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
