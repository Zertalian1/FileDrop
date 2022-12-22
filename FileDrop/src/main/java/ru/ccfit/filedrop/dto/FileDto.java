package ru.ccfit.filedrop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private Long id;

    private String path;

    private String name;

    private UserDto ownerUser;

    private OffsetDateTime createDateTime;

    private OrderDto order;

    public FileDto(String name, UserDto ownerUser, OrderDto order) {
        this.name = name;
        this.ownerUser = ownerUser;
        this.createDateTime = OffsetDateTime.now();
        this.order = order;
    }
}
