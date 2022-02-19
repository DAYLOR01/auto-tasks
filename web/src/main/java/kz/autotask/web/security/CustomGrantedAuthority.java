package kz.autotask.web.security;

import kz.autotask.web.data.entity.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority extends Role implements GrantedAuthority {

    public CustomGrantedAuthority(Role role) {
        this.setName(role.getName());
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
