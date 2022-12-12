package ru.ccfit.filedrop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ccfit.filedrop.enumeration.Status;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    private Status status;

    private UserDto user;

    private OffsetDateTime createDateTime;

    private OffsetDateTime storeUpTo;

    private List<FileDto> files;
}
