package dev.mkennedy.blog.model;

import javax.validation.constraints.NotBlank;
import dev.mkennedy.blog.entity.Reply;

public class UpdateReplyForm {
    @NotBlank
    private String content;

    public UpdateReplyForm() {
    }

    public void updateReply(Reply reply) {
        reply.setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
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
        UpdateReplyForm other = (UpdateReplyForm) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UpdateReplyForm [content=" + content + "]";
    }
}
