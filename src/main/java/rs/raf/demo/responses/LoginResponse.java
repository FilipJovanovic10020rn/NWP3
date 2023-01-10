package rs.raf.demo.responses;

import lombok.Data;
import rs.raf.demo.model.Permission;

import java.util.List;


public class LoginResponse {
    private String jwt;
    private List<Permission> permissions;

    public LoginResponse(String jwt, List<Permission> permissions) {
        this.jwt = jwt;
        this.permissions = permissions;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
