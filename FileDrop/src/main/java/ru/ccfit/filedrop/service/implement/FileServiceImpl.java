package ru.ccfit.filedrop.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ccfit.filedrop.mapper.FileMapper;
import ru.ccfit.filedrop.repository.FileRepository;
import ru.ccfit.filedrop.service.interfaces.FileService;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
}
