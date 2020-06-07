package dev.mkennedy.blog.model;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewReplyForm {

    @NotBlank
    private String content;
    @NotNull
    private UUID userId;
    @NotNull
    private Long postId;
    private Long replyId;

    public NewReplyForm() {}

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public UUID getUserId() {
      return userId;
    }

    public void setUserId(UUID userId) {
      this.userId = userId;
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
      result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
      if (userId == null) {
        if (other.userId != null)
          return false;
      } else if (!userId.equals(other.userId))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ReplyForm [content=" + content + ", postId=" + postId
          + ", replyId=" + replyId + ", userId=" + userId + "]";
    }
}
