package ru.ccfit.filedrop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ccfit.filedrop.entity.File;

public interface FileRepository extends CrudRepository<File,Long> {
}
