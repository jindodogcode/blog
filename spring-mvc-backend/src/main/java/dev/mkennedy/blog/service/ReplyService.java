package dev.mkennedy.blog.service;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;

import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;

public interface ReplyService {

    public Reply save(Reply reply);

    public Reply findById(long id);

    public Page<Reply> findAllRecent(int page, int pageSize);

    public Page<Reply> findByPost(Post post, int page, int pageSize);

    public Page<Reply> findByPostAndCreatedAfter(Post post, ZonedDateTime after, int page, int pageSize);

    public Page<Reply> findByUser(User user, int page, int pageSize);

    public Page<Reply> findByReply(Reply reply, int page, int pageSize);
}
