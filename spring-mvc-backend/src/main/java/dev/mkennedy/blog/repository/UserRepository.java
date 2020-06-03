package dev.mkennedy.blog.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE u.email=?1 OR u.username=?1")
    Optional<User> findByEmailOrUsername(String search);
}
