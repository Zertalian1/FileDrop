package ru.ccfit.filedrop.mapper;

import org.mapstruct.Mapper;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.entity.File;

@Mapper(componentModel = "spring")
public interface FileMapper {
    File fileDtoToFile(FileDto fileDto);

    FileDto fileToFileDto(File file);
}
