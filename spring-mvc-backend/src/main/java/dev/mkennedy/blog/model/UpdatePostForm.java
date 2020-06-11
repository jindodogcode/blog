package dev.mkennedy.blog.model;

import javax.validation.constraints.NotBlank;

public class UpdatePostForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public UpdatePostForm() {}

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
      UpdatePostForm other = (UpdatePostForm) obj;
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
      return "UpdatePostForm [content=" + content + ", title=" + title + "]";
    }
}
