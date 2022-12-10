package ru.ccfit.filedrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ccfit.filedrop.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT u FROM User u WHERE u.name = :username")
    Optional<User> findByUsername(String username);
}
