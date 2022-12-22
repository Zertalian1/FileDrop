package ru.ccfit.filedrop.service.interfaces;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.entity.File;
import ru.ccfit.filedrop.exception.*;

import java.util.List;

public interface FileService {
    /**
     * Скачивание файла
     *
     * @param fileId id файла, который необходимо скачать
     * @return Resource ресурс, которых хранит в себе файл
     * @throws NotFoundException файл не найден
     * @throws FileException ошибка при скачивании файла
     */
    ByteArrayResource downloadFile(Long  fileId);
    /**
     * Нахождение нужного файла по его ID
     *
     * @param fileId id файла, который необходимо найти
     * @return FileDto найденный файл
     * @throws NotFoundException файл не найден
     */
    FileDto getFileById(Long fileId);
    /**
     * Удаляет файл из базы данных
     *
     * @param file удаляемый файл
     * @throws FileException ошибка при удалении файла
     */
    void deleteFile(File file);
    /**
     * Сохраняет файл в файловой системе
     *
     * @param fileDto       параметры сохраняемого файла
     * @param multipartFile файл
     * @throws FileException ошибка при загрузки файла
     */
    void saveFile(FileDto fileDto, MultipartFile multipartFile);

    /**
     *
     * @param id id заказа
     * @return список файлов в заказе
     */
    List<File> getFilesByOrderId(Long id);

    void deleteFiles(List<File> filesByOrderId, String l);

    /*List<FileDto>*/
}
