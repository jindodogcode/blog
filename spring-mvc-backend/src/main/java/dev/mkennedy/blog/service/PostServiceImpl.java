package dev.mkennedy.blog.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Override
    public Post save(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post with id: " + id + " not found"));
    }

    @Override
    public Page<Post> findAllByUsername(String username, Pageable pageable) {
        return postRepo.findAllByUsername(username, pageable);
    }
}
