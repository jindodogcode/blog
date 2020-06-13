package dev.mkennedy.blog.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import dev.mkennedy.blog.entity.User;

public class UpdateUserForm {

    @NotNull
    @Email
    private String email;
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

    public UpdateUserForm() {
    }

    public UpdateUserForm(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void updateUser(User user) {
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result
                + ((lastName == null) ? 0 : lastName.hashCode());
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
        UpdateUserForm other = (UpdateUserForm) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "UpdateUserForm [email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + "]";
    }
}
