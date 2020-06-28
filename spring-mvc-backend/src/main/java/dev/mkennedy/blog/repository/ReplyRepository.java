package dev.mkennedy.blog.repository;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findByPost(Post post, Pageable pageable);

    Page<Reply> findByPostAndCreatedAfter(Post post, ZonedDateTime after, Pageable pagealbe);

    Page<Reply> findByUser(User user, Pageable pageable);

    Page<Reply> findByReply(Reply reply, Pageable pageable);
}
