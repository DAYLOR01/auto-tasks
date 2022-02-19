package kz.autotask.web.security;

import kz.autotask.web.data.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails {

    private List<CustomGrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(User entity) {
        this.setUsername(entity.getUsername());
        this.setPassword(entity.getPassword());
        this.setName(entity.getName());
        this.setActive(entity.isActive());
        entity.getRoles().forEach(role -> this.authorities.add(new CustomGrantedAuthority(role)));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
