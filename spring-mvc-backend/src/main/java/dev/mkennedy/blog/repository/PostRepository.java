package dev.mkennedy.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findByUser(User user);
}
