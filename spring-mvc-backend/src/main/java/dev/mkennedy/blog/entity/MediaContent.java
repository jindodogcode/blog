package dev.mkennedy.blog.entity;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Media_Content")
public class MediaContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "uri")
    private URI uri;
    @Column(name = "uploaded")
    private LocalDateTime uploaded;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public MediaContent() {}

    public MediaContent(URI uri, User user) {
        this.uri = uri;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        this.uploaded = LocalDateTime.now();
    }

    @JsonGetter("user_id")
    protected UUID getUserId() {
        if (user != null)
            return user.getId();

        return null;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public URI getUri() {
      return uri;
    }

    public void setUri(URI uri) {
      this.uri = uri;
    }

    public LocalDateTime getUploaded() {
      return uploaded;
    }

    public void setUploaded(LocalDateTime uploaded) {
      this.uploaded = uploaded;
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
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((uploaded == null) ? 0 : uploaded.hashCode());
      result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
      MediaContent other = (MediaContent) obj;
      if (id == null) {
        if (other.id != null)
          return false;
      } else if (!id.equals(other.id))
        return false;
      if (uploaded == null) {
        if (other.uploaded != null)
          return false;
      } else if (!uploaded.equals(other.uploaded))
        return false;
      if (uri == null) {
        if (other.uri != null)
          return false;
      } else if (!uri.equals(other.uri))
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
      return "MediaContent [id=" + id + ", uploaded=" + uploaded + ", uri="
          + uri + ", user=" + user + "]";
    }
}
