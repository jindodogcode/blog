package dev.mkennedy.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Iterable<Reply> findByUser(User user);

    Iterable<Reply> findByPost(Post post);

    Iterable<Reply> findByReply(Reply reply);

    @Query("SELECT r FROM Replies r WHERE r.user = (SELECT u FROM Users u WHERE u.username = ?1)")
    Page<Reply> findAllByUsername(String username, Pageable pageable);
}
