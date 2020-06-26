package dev.mkennedy.blog.repository;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByCreatedAfter(ZonedDateTime created, Pageable pageable);

    Iterable<Post> findAllByUser(User user);

    @Query("SELECT p FROM Posts p WHERE p.user = (SELECT u FROM Users u WHERE u.username = ?1)")
    Iterable<Post> findAllByUsername(String username);

    @Query("SELECT p FROM Posts p WHERE p.user = (SELECT u FROM Users u WHERE u.username = ?1)")
    Page<Post> findAllByUsername(String username, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.user = (SELECT u FROM Users u WHERE u.username = ?1) AND p.created > ?2")
    Page<Post> findAllByUsernameAndCreatedAfter(String username, ZonedDateTime created, Pageable pageable);
}
