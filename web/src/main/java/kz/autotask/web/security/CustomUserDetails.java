package kz.autotask.web.security;

import kz.autotask.web.data.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails {

    private final List<CustomGrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(User entity) {
        this.setUsername(entity.getUsername());
        this.setPassword(entity.getPassword());
        this.setName(entity.getName());
        this.setIsActive(entity.getIsActive());
        entity.getRoles().forEach(role -> this.authorities.add(new CustomGrantedAuthority(role)));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return getIsActive();
    }
}
