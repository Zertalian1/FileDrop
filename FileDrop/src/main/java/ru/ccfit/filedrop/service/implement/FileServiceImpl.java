package ru.ccfit.filedrop.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.entity.File;
import ru.ccfit.filedrop.entity.Order;
import ru.ccfit.filedrop.exception.FileException;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.mapper.FileMapper;
import ru.ccfit.filedrop.repository.FileRepository;
import ru.ccfit.filedrop.service.interfaces.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Value("server.file.root.path")
    private final String rootPath;

    private final Path root = Paths.get(rootPath);

    /**
     * Скачивание файла
     *
     * @param fileDto файл, который необходимо скачать
     * @return Resource ресурс, которых хранит в себе файл
     */
    @Override
    public Resource downloadFile(FileDto fileDto) {
        File file = fileRepository.findById(fileDto.getId()).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileDto.getId() + " не найден!")
        );

        Path path = Paths.get(file.getPath());
        ByteArrayResource resource;

        try {
            resource = new ByteArrayResource
                    (Files.readAllBytes(path));
        } catch (IOException e) {
            throw new FileException("Ошибка при скачивании файла");
        }

        return resource;
    }

    /**
     * Нахождение нужного файла по его ID
     *
     * @param fileId файл, который необходимо найти
     * @return FileDto найденный файл
     */
    @Override
    public FileDto getFileById(Long fileId) {
        Optional<File> order = fileRepository.findById(fileId);
        return order.map(fileMapper::fileToFileDto).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileId + " не найден!")
        );
    }

    /**
     * Удаляет файл из базы данных
     *
     * @param fileDto удаляемый файл
     */
    @Override
    public void deleteFile(FileDto fileDto) {
        File file = fileRepository.findById(fileDto.getId()).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileDto.getId() + " не найден!")
        );
        try {
            Files.delete(Path.of(file.getPath()));
        } catch (IOException e) {
            throw new FileException("Ошибка при удалении файла");
        }
        fileRepository.delete(file);
    }

    /**
     * Сохраняет файл в файловой системе
     *
     * @param fileDto       параметры сохраняемого файла
     * @param multipartFile файл
     * @return FileDto данные файла после сохранения в системе
     */
    @Override
    public FileDto saveFile(FileDto fileDto, MultipartFile multipartFile) {
        fileDto.setName(multipartFile.getName());

        try {
            Files.copy(multipartFile.getInputStream(), root.resolve(fileDto.getOwnerUser().getId().toString()).resolve(fileDto.getName()));
        } catch (IOException e) {
            throw new FileException("Ошибка при сохранении файла");
        }

        File file = fileRepository.save(fileMapper.fileDtoToFile(fileDto));
        return fileMapper.fileToFileDto(file);
    }
}
