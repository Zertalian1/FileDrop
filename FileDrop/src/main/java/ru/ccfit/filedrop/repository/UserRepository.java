package ru.ccfit.filedrop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ccfit.filedrop.entity.User;

public interface UserRepository extends CrudRepository<User,Long> {
}
