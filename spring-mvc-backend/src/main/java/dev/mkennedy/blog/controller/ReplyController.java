package dev.mkennedy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.mkennedy.blog.PagingDefaults;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.service.ReplyService;

@RestController
@RequestMapping("/api/v1/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public Page<Reply> getReplies(
            @RequestParam(
                value = "page",
                required = false,
                defaultValue = PagingDefaults.PAGE
            ) int page,
            @RequestParam(
                value = "pagesize",
                required = false,
                defaultValue = PagingDefaults.PAGESIZE
            ) int pageSize) {
        return replyService.findAllRecent(page, pageSize);
    }

    @GetMapping("/{id}")
    public Reply getReplyById(@PathVariable("id") long id) {
        return replyService.findById(id);
    }

    @GetMapping("/{id}/replies")
    public Page<Reply> getReplyReplies(
            @PathVariable("id") long id,
            @RequestParam(
                value = "page",
                required = false,
                defaultValue = PagingDefaults.PAGE
            ) int page,
            @RequestParam(
                value = "pagesize",
                required = false,
                defaultValue = PagingDefaults.PAGESIZE
            ) int pageSize) {
        // check that Reply with id exists
        replyService.findById(id);

        return replyService.findAllByReplyId(id, page, pageSize);
    }
}
