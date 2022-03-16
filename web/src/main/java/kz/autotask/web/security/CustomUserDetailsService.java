package kz.autotask.web.security;

import kz.autotask.web.data.entity.User;
import kz.autotask.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findActiveUserByUsername(username);
        if(user == null || user.getRoles().isEmpty())
            throw new UsernameNotFoundException("User with username: " + username + " - not found or not activated.");
        return new CustomUserDetails(user);
    }
}
