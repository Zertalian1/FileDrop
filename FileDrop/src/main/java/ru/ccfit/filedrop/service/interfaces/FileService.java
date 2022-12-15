package ru.ccfit.filedrop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.dto.OrderDto;
import org.springframework.core.io.Resource;
import java.util.List;

public interface FileService {
    Resource downloadFile(FileDto fileDto);
    FileDto getFileById(Long fileId);
    void deleteFile(FileDto fileDto);
    FileDto saveFile(FileDto fileDto, MultipartFile file);
}
