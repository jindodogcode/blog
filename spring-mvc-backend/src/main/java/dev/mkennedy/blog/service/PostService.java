package dev.mkennedy.blog.service;

import org.springframework.data.domain.Page;
import dev.mkennedy.blog.entity.Post;

public interface PostService {
    
    public Post save(Post post);

    public Post findById(long id);

    public Page<Post> findAllRecent(int page, int pageSize);

    public Page<Post> findAllByUsername(String username, int page, int pageSize);
}
