package dev.mkennedy.blog.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.UserSecurity;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, UUID> {
}
