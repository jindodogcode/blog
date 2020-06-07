package dev.mkennedy.blog.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name = "Users")
public class User {

    @Id
    @Column(name = "id")
    private UUID id;
    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "last_logged_in", nullable = true)
    private LocalDateTime lastLoggedIn;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonProperty(access = Access.WRITE_ONLY)
    private UserSecurity security;

    public User() {}

    public User(String email, String username, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email,
            String username,
            String firstName,
            String lastName,
            UserSecurity security) {
        this(email, username, firstName, lastName);
        this.security = security;
    }

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.created = LocalDateTime.now();
    }

    public UUID getId() {
      return id;
    }

    public void setId(UUID id) {
      this.id = id;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public LocalDateTime getCreated() {
      return created;
    }

    public void setCreated(LocalDateTime created) {
      this.created = created;
    }

    public LocalDateTime getLastLoggedIn() {
      return lastLoggedIn;
    }

    public void setLastLoggedIn(LocalDateTime lastLoggedIn) {
      this.lastLoggedIn = lastLoggedIn;
    }

    public UserSecurity getSecurity() {
      return security;
    }

    public void setSecurity(UserSecurity security) {
      this.security = security;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((created == null) ? 0 : created.hashCode());
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result =
          prime * result + ((firstName == null) ? 0 : firstName.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result
          + ((lastLoggedIn == null) ? 0 : lastLoggedIn.hashCode());
      result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
      result = prime * result + ((security == null) ? 0 : security.hashCode());
      result = prime * result + ((username == null) ? 0 : username.hashCode());
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
      User other = (User) obj;
      if (created == null) {
        if (other.created != null)
          return false;
      } else if (!created.equals(other.created))
        return false;
      if (email == null) {
        if (other.email != null)
          return false;
      } else if (!email.equals(other.email))
        return false;
      if (firstName == null) {
        if (other.firstName != null)
          return false;
      } else if (!firstName.equals(other.firstName))
        return false;
      if (id == null) {
        if (other.id != null)
          return false;
      } else if (!id.equals(other.id))
        return false;
      if (lastLoggedIn == null) {
        if (other.lastLoggedIn != null)
          return false;
      } else if (!lastLoggedIn.equals(other.lastLoggedIn))
        return false;
      if (lastName == null) {
        if (other.lastName != null)
          return false;
      } else if (!lastName.equals(other.lastName))
        return false;
      if (security == null) {
        if (other.security != null)
          return false;
      } else if (!security.equals(other.security))
        return false;
      if (username == null) {
        if (other.username != null)
          return false;
      } else if (!username.equals(other.username))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "User [created=" + created + ", email=" + email + ", firstName="
          + firstName + ", id=" + id + ", lastLoggedIn=" + lastLoggedIn
          + ", lastName=" + lastName + ", username="
          + username + "]";
    }
}
