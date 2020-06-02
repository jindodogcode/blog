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

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    @NotNull
    @Column(name = "content", nullable = false)
    private String content;
    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "edited", nullable = true)
    private LocalDateTime edited;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public Post() {};

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

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
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

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((content == null) ? 0 : content.hashCode());
      result = prime * result + ((created == null) ? 0 : created.hashCode());
      result = prime * result + ((edited == null) ? 0 : edited.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((title == null) ? 0 : title.hashCode());
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
      Post other = (Post) obj;
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
      if (title == null) {
        if (other.title != null)
          return false;
      } else if (!title.equals(other.title))
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
      return "Post [content=" + content + ", created=" + created + ", edited="
          + edited + ", id=" + id + ", title=" + title + ", user=" + user + "]";
    }
}
