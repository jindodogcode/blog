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

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByUserAndCreatedAfter(User user, ZonedDateTime created, Pageable pageable);
}
