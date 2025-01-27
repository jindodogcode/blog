package dev.mkennedy.blog.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Size(min = 3, max = 32)
    @Pattern(
        regexp = "^\\p{L}[\\p{L}\\p{N}\\p{Pd}\\p{Pc}]+$",
        message = "must start with a letter and only contain letters, numbers, dashes, and underscores"
    )
    private String username;
    @NotBlank
    @Size(max = 32)
    @Pattern(
        regexp = "^\\p{L}[\\p{L}\\p{Pd} ]+$",
        message = "must start with a letter and only contain letters, dashes, and spaces"
    )
    private String firstName;
    @NotBlank
    @Size(max = 32)
    @Pattern(
        regexp = "^\\p{L}[\\p{L}\\p{Pd} ]+$",
        message = "must start with a letter and only contain letters, dashes, and spaces"
    )
    private String lastName;
    @NotNull
    @Size(max = 255)
    private String about;
    @NotNull
    @Size(min = 8, max = 64)
    private String password;

    public NewUserForm() {}

    public User toUser(PasswordEncoder encoder, Role role) {
        UserSecurity security = new UserSecurity(encoder.encode(password), role);
        return new User(email, username, firstName, lastName, about, security);
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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
        result = prime * result + ((about == null) ? 0 : about.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
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
        if (about == null) {
            if (other.about != null)
                return false;
        } else if (!about.equals(other.about))
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
        return "NewUserForm [about=" + about + ", email=" + email + ", firstName=" + firstName + ", lastName="
                + lastName + ", password=" + password + ", username=" + username + "]";
    }
}
