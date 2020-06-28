package dev.mkennedy.blog.controller;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
            ) int pageSize,
            @RequestParam(
                value = "after",
                required = false
            ) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime after) {
        System.out.println(after);
        if (after == null) {
            return postService.findAllRecent(page, pageSize);
        } else {
            return postService.findByCreatedAfter(after, page, pageSize);
        }
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
            ) int pageSize,
            @RequestParam(
                value = "after",
                required = false
            ) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime after) {
        // check that Post with id exists
        Post post = postService.findById(id);

        if (after == null) {
            return replyService.findByPost(post, page, pageSize);
        } else {
            return replyService.findByPostAndCreatedAfter(post, after, page, pageSize);
        }
    }
}
