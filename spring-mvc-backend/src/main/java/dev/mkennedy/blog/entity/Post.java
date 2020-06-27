package dev.mkennedy.blog.entity;

import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@Entity(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    @Lob @Basic(fetch = FetchType.LAZY)
    private String content;
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;
    @Column(name = "edited", nullable = true)
    private ZonedDateTime edited;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnore
    private User user;

    public Post() {
    }

    public Post(String title, String content, User user) {
        setTitle(title);
        setContent(content);
        this.user = user;
    }

    @PrePersist
    protected void prePersist() {
        this.created = ZonedDateTime.now();
    }

    @JsonGetter("username")
    protected String getUsername() {
        if (user != null)
            return user.getUsername();

        return null;
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
        this.title = Jsoup.clean(title, Whitelist.basic());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = Jsoup.clean(content, Whitelist.basicWithImages());
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getEdited() {
        return edited;
    }

    public void setEdited(ZonedDateTime edited) {
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
        return true;
    }

    @Override
    public String toString() {
        return "Post [content=" + content + ", created=" + created + ", edited="
                + edited + ", id=" + id + ", title=" + title + "]";
    }
}
