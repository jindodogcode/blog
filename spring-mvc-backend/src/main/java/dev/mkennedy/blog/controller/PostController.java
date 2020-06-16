package dev.mkennedy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.mkennedy.blog.PagingDefaults;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.service.PostService;
import dev.mkennedy.blog.service.ReplyService;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public Page<Post> getPosts(
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
        return postService.findAllRecent(page, pageSize);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") long id) {
        return postService.findById(id);
    }

    @GetMapping("/{id}/replies")
    public Page<Reply> getPostReplies(@PathVariable("id") long id,
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
        // check that Post with id exists
        postService.findById(id);

        return replyService.findAllByPostId(id, page, pageSize);
    }
}
