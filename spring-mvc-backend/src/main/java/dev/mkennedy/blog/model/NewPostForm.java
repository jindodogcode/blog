package dev.mkennedy.blog.model;

import javax.validation.constraints.NotBlank;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;

public class NewPostForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public NewPostForm() {}

    public Post toPost(User user) {
        return new Post(title, content, user);
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

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((content == null) ? 0 : content.hashCode());
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
      NewPostForm other = (NewPostForm) obj;
      if (content == null) {
        if (other.content != null)
          return false;
      } else if (!content.equals(other.content))
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
      return "NewPostForm [content=" + content + ", title=" + title + "]";
    }
}
