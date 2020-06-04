package dev.mkennedy.blog.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.UserSecurity;

@Repository
public interface UserSecurityRepository extends CrudRepository<UserSecurity, UUID> {
}
