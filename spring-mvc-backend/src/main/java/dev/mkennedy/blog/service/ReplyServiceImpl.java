package dev.mkennedy.blog.service;

import javax.persistence.EntityNotFoundException;
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
    public Page<Reply> findAllByPostId(long id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "created"));

        return replyRepo.findAllByPostId(id, pageable);
    }

    @Override
    public Page<Reply> findAllByUsername(String username, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "created"));

        return replyRepo.findAllByUsername(username, pageable);
    }
}
