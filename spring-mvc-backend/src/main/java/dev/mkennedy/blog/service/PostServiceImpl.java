package dev.mkennedy.blog.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    public Post findById(long id) {
        return postRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post with id: " + id + " not found"));
    }

    @Override
    public Page<Post> findAllRecent(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return postRepo.findAll(pageable);
    }

    @Override
    public Page<Post> findAllByUsername(String username, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return postRepo.findAllByUsername(username, pageable);
    }
}
