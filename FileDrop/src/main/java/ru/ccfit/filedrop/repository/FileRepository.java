package ru.ccfit.filedrop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ccfit.filedrop.entity.File;

import java.util.List;

public interface FileRepository extends CrudRepository<File,Long> {

    List<File> getFileByOrder_Id(Long id);
}
