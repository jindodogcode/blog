package dev.mkennedy.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dev.mkennedy.blog.entity.MediaContent;
import dev.mkennedy.blog.entity.User;

@Repository
public interface MediaContentRepository extends CrudRepository<MediaContent, Long> {

    Iterable<MediaContent> findByUser(User user);
}
