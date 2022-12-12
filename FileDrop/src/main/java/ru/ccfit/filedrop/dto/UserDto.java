package ru.ccfit.filedrop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ccfit.filedrop.enumeration.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private Role role;

    private String phone;

    private String password;
}
