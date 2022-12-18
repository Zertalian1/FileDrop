package ru.ccfit.filedrop.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.entity.File;
import ru.ccfit.filedrop.exception.FileException;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.mapper.FileMapper;
import ru.ccfit.filedrop.repository.FileRepository;
import ru.ccfit.filedrop.service.interfaces.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    private final Path rootPath;

    @Override
    public Resource downloadFile(Long  fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileId + " не найден!")
        );

        ByteArrayResource resource;

        try {
            resource = new ByteArrayResource
                    (Files.readAllBytes(rootPath.resolve(file.getPath())));
        } catch (IOException e) {
            throw new FileException("Ошибка при скачивании файла");
        }

        return resource;
    }

    @Override
    public FileDto getFileById(Long fileId) {
        Optional<File> order = fileRepository.findById(fileId);
        return order.map(fileMapper::fileToFileDto).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileId + " не найден!")
        );
    }

    @Override
    public void deleteFile(File file) {
        try {
            Files.delete(rootPath.resolve(file.getPath()));
        } catch (IOException e) {
            throw new FileException("Ошибка при удалении файла");
        }
        fileRepository.delete(file);
    }

    @Override
    public void saveFile(FileDto fileDto, MultipartFile multipartFile) {
        Path filePath = getPathFile(fileDto);

        try {
            Files.copy(multipartFile.getInputStream(), rootPath.resolve(filePath));
        } catch (IOException e) {
            throw new FileException("Ошибка при сохранении файла");
        }

        fileDto.setPath(filePath.toString());
        File file = fileRepository.save(fileMapper.fileDtoToFile(fileDto));

        fileMapper.fileToFileDto(file);
    }

    /**
     * Возвращает относительный путь необходимого файла
     *
     * @param fileDto dto файла
     * @return Path относительный путь
     */
    private Path getPathFile(FileDto fileDto) {
       return Path.of(fileDto.getOwnerUser().getId().toString()).resolve(fileDto.getName());
    }
}
