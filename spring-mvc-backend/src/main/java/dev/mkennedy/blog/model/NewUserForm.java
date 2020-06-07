package dev.mkennedy.blog.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;

public class NewUserForm {
    
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 4)
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @Size(min = 8)
    private String password;

    public NewUserForm() {}

    public User toUser(PasswordEncoder encoder, Role role) {
        UserSecurity security = new UserSecurity(encoder.encode(password), role);
        return new User(email, username, firstName, lastName, security);
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

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result =
          prime * result + ((firstName == null) ? 0 : firstName.hashCode());
      result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
      result = prime * result + ((password == null) ? 0 : password.hashCode());
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
      NewUserForm other = (NewUserForm) obj;
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
      if (lastName == null) {
        if (other.lastName != null)
          return false;
      } else if (!lastName.equals(other.lastName))
        return false;
      if (password == null) {
        if (other.password != null)
          return false;
      } else if (!password.equals(other.password))
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
      return "UserForm [email=" + email + ", firstName=" + firstName
          + ", lastname=" + lastName + ", password=" + password + ", username="
          + username + "]";
    }
}