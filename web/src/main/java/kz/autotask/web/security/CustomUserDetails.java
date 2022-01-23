package kz.autotask.web.security;

import kz.autotask.web.data.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends User implements UserDetails {

    public static CustomUserDetails fromEntity(User entity) {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUsername(entity.getUsername());
        userDetails.setPassword(entity.getPassword());
        userDetails.setName(entity.getName());
        userDetails.setActive(entity.isActive());
        return userDetails;
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
