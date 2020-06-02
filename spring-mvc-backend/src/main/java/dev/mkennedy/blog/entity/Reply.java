package dev.mkennedy.blog.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity(name = "Replies")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "content", nullable = false)
    private String content;
    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "edited", nullable = true)
    private LocalDateTime edited;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = true, updatable = false)
    private Reply reply;

    public Reply() {}

    public Reply(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public LocalDateTime getCreated() {
      return created;
    }

    public void setCreated(LocalDateTime created) {
      this.created = created;
    }

    public LocalDateTime getEdited() {
      return edited;
    }

    public void setEdited(LocalDateTime edited) {
      this.edited = edited;
    }

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public Post getPost() {
      return post;
    }

    public void setPost(Post post) {
      this.post = post;
    }

    public Reply getReply() {
      return reply;
    }

    public void setReply(Reply reply) {
      this.reply = reply;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((content == null) ? 0 : content.hashCode());
      result = prime * result + ((created == null) ? 0 : created.hashCode());
      result = prime * result + ((edited == null) ? 0 : edited.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((post == null) ? 0 : post.hashCode());
      result = prime * result + ((reply == null) ? 0 : reply.hashCode());
      result = prime * result + ((user == null) ? 0 : user.hashCode());
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
      Reply other = (Reply) obj;
      if (content == null) {
        if (other.content != null)
          return false;
      } else if (!content.equals(other.content))
        return false;
      if (created == null) {
        if (other.created != null)
          return false;
      } else if (!created.equals(other.created))
        return false;
      if (edited == null) {
        if (other.edited != null)
          return false;
      } else if (!edited.equals(other.edited))
        return false;
      if (id == null) {
        if (other.id != null)
          return false;
      } else if (!id.equals(other.id))
        return false;
      if (post == null) {
        if (other.post != null)
          return false;
      } else if (!post.equals(other.post))
        return false;
      if (reply == null) {
        if (other.reply != null)
          return false;
      } else if (!reply.equals(other.reply))
        return false;
      if (user == null) {
        if (other.user != null)
          return false;
      } else if (!user.equals(other.user))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Reply [content=" + content + ", created=" + created + ", edited="
          + edited + ", id=" + id + ", post=" + post + ", reply=" + reply
          + ", user=" + user + "]";
    }
}
