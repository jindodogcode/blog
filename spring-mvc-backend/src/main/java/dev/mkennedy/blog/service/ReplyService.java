package dev.mkennedy.blog.service;

import org.springframework.data.domain.Page;
import dev.mkennedy.blog.entity.Reply;

public interface ReplyService {

    public Reply save(Reply reply);

    public Reply findById(long id);

    public Page<Reply> findAllByPostId(long id, int page, int pageSize);

    public Page<Reply> findAllByUsername(String username, int page, int pageSize);
}
