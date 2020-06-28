package dev.mkennedy.blog.service;

import java.time.ZonedDateTime;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.repository.ReplyRepository;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepo;

    @Override
    public Reply save(Reply reply) {
        return replyRepo.save(reply);
    }

    @Override
    public Reply findById(long id) {
        return replyRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Reply with id: " + id + " not found"));
    }

    @Override
    public Page<Reply> findAllRecent(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return replyRepo.findAll(pageable);
    }

    @Override
    public Page<Reply> findByPost(Post post, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "created"));

        return replyRepo.findByPost(post, pageable);
    }

    @Override
    public Page<Reply> findByPostAndCreatedAfter(Post post, ZonedDateTime after, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "created"));

        return replyRepo.findByPostAndCreatedAfter(post, after, pageable);
    }

    @Override
    public Page<Reply> findByUser(User user, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return replyRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Reply> findByReply(Reply reply, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return replyRepo.findByReply(reply, pageable);
    }
}
