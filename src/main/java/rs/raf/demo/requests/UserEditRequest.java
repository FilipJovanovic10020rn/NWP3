package rs.raf.demo.requests;

import rs.raf.demo.model.Permission;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserEditRequest {


    private String firstName;
    private String lastName;
    private String email;
    private Set<Permission> permissions;

    public UserEditRequest(String firstName, String lastName, String email, String password, Set<Permission> permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.permissions = permissions;
    }

    public UserEditRequest() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
