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

    Optional<User> findByUserName(String userName);

    @Query("SELECT u FROM Users u WHERE u.email=?1 OR u.userName=?1")
    Optional<User> findByEmailOrUserName(String search);
}
