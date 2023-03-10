package ru.ccfit.filedrop.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.entity.File;
import ru.ccfit.filedrop.entity.Order;
import ru.ccfit.filedrop.exception.FileException;
import ru.ccfit.filedrop.exception.IntegrationException;
import ru.ccfit.filedrop.exception.NotFoundException;
import ru.ccfit.filedrop.mapper.FileMapper;
import ru.ccfit.filedrop.repository.FileRepository;
import ru.ccfit.filedrop.service.interfaces.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    private final Path rootPath;

    @Override
    public ByteArrayResource downloadFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileId + " не найден!")
        );

        ByteArrayResource resource;

        try {
            resource = new ByteArrayResource
                    (Files.readAllBytes(rootPath.resolve(file.getPath()).resolve(file.getName())));
        } catch (IOException e) {
            throw new FileException("Ошибка при скачивании файла");
        }

        return resource;
    }

    @Override
    public FileDto getFileById(Long fileId) {
        Optional<File> files = fileRepository.findById(fileId);
        return files.map(fileMapper::fileToFileDto).orElseThrow(
                () -> new NotFoundException("Файл с id: " + fileId + " не найден!")
        );
    }

    @Override
    public void deleteFile(FileDto file) {
        try {
            Files.delete(rootPath.resolve(file.getPath()).resolve(file.getName()));
        } catch (IOException e) {
            throw new FileException("Ошибка при удалении файла");
        }
        fileRepository.delete(fileMapper.fileDtoToFile(file));
    }

    @Override
    public void saveFile(FileDto fileDto, MultipartFile multipartFile) {
        File file;
        try {
            file = fileRepository.save(fileMapper.fileDtoToFile(fileDto));
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrationException("В заказе есть файл с таким именем");
        }

        Path filePath = getPathFile(file);

        try (InputStream inputStream = multipartFile.getInputStream()){
            Files.createDirectories(rootPath.resolve(filePath));
            Files.copy(inputStream, rootPath.resolve(filePath).resolve(file.getName()));
        } catch (IOException e) {
            fileRepository.delete(file);
            throw new FileException("Ошибка при сохранении файла");
        }

        file.setPath(filePath.toString());
        fileRepository.save(file);
    }

    @Override
    public List<FileDto> getFilesByOrderId(Long id) {
        return listFileToListFileDto(fileRepository.getFilesByOrderId(id));
    }

    private List<FileDto> listFileToListFileDto(List<File> files) {
        return files.stream().map(fileMapper::fileToFileDto).collect(Collectors.toList());
    }

    /**
     * Возвращает относительный путь необходимого файла
     *
     * @param file файл
     * @return Path относительный путь
     */
    private Path getPathFile(File file) {
        return Path.of("Order" + file.getOrder().getId());
    }
}
