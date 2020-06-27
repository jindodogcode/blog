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

import dev.mkennedy.blog.entity.Reply;
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
    public Page<Reply> findAllByPostId(long id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "created"));

        return replyRepo.findAllByPostId(id, pageable);
    }

    @Override
    public Page<Reply> findAllByPostIdAndCreatedAfter(long id, ZonedDateTime after, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "created"));

        return replyRepo.findAllByPostIdAndCreatedAfter(id, after, pageable);
    }

    @Override
    public Page<Reply> findAllByUsername(String username, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return replyRepo.findAllByUsername(username, pageable);
    }

    @Override
    public Page<Reply> findAllByReplyId(long id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return replyRepo.findAllByReplyId(id, pageable);
    }
}
