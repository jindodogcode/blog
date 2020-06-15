package dev.mkennedy.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.mkennedy.blog.entity.Post;

public interface PostService {
    
    public Post save(Post post);

    public Post findById(Long id);

    public Page<Post> findAllByUsername(String username, Pageable pageable);
}
