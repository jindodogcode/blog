package dev.mkennedy.blog.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;

public class NewReplyForm {

    @NotBlank
    private String content;
    @NotNull
    private Long postId;
    private Long replyId;

    public NewReplyForm() {
    }

    public Reply toReply(User user) {
        return new Reply(content, user);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((postId == null) ? 0 : postId.hashCode());
        result = prime * result + ((replyId == null) ? 0 : replyId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewReplyForm other = (NewReplyForm) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (postId == null) {
            if (other.postId != null)
                return false;
        } else if (!postId.equals(other.postId))
            return false;
        if (replyId == null) {
            if (other.replyId != null)
                return false;
        } else if (!replyId.equals(other.replyId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NewReplyForm [content=" + content + ", postId=" + postId
                + ", replyId=" + replyId + "]";
    }
}
