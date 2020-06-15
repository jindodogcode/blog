package dev.mkennedy.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.mkennedy.blog.entity.Reply;

public interface ReplyService {

    public Reply save(Reply reply);

    public Reply findById(Long id);

    public Page<Reply> findAllByUsername(String username, Pageable pageable);
}
