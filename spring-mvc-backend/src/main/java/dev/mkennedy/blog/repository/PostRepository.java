package dev.mkennedy.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Iterable<Post> findAllByUser(User user);

    @Query("SELECT p FROM Posts p WHERE p.user = (SELECT u FROM Users u WHERE u.username = ?1)")
    Iterable<Post> findAllByUsername(String username);
}
