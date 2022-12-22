package ru.ccfit.filedrop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ccfit.filedrop.enumeration.Status;

import javax.management.ConstructorParameters;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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
    public String getCreateDateTimeAsSting(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return createDateTime.format(fmt);
    }

    public OrderDto(Status status, UserDto user, OffsetDateTime createDateTime) {
        this.createDateTime = createDateTime;
        this.status = status;
        this.user = user;
    }
}
