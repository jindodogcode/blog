package dev.mkennedy.blog.repository;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.mkennedy.blog.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Replies r WHERE r.post = (SELECT p FROM Posts p WHERE p.id = ?1)")
    Page<Reply> findAllByPostId(long id, Pageable pageable);

    @Query("SELECT r FROM Replies r WHERE r.post = (SELECT p FROM Posts p WHERE p.id = ?1) AND r.created > ?2")
    Page<Reply> findAllByPostIdAndCreatedAfter(long id, ZonedDateTime after, Pageable pagealbe);

    @Query("SELECT r FROM Replies r WHERE r.user = (SELECT u FROM Users u WHERE u.username = ?1)")
    Page<Reply> findAllByUsername(String username, Pageable pageable);

    @Query("SELECT r FROM Replies r WHERE r.reply = (SELECT rt FROM Replies rt WHERE rt.id = ?1)")
    Page<Reply> findAllByReplyId(long id, Pageable pageable);
}
