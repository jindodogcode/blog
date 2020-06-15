package dev.mkennedy.blog.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Reply findById(Long id) {
        return replyRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Reply with id: " + id + " not found"));
    }

    @Override
    public Page<Reply> findAllByUsername(String username, Pageable pageable) {
        return replyRepo.findAllByUsername(username, pageable);
    }
}
