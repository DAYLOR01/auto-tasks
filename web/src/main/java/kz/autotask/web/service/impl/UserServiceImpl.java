package kz.autotask.web.service.impl;

import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.entity.User;
import kz.autotask.web.data.entity.enums.TagUsability;
import kz.autotask.web.data.repository.UserRepository;
import kz.autotask.web.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public User findActiveUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCaseAndIsActiveIsTrue(username);
    }

    @Override
    public User findActiveByUsernameAndPassword(String username, String password) {
        User user = findActiveUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void changeActive(String username, boolean isActive) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setIsActive(isActive);
        userRepository.save(user);
    }

    @Override
    public List<Tag> findMainTagsByUsername(String username) {
        List<Tag> result = new ArrayList<>();
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.getTags().forEach(tag -> {
            if(tag.getUsability() == TagUsability.ALL)
                result.add(tag);
        });
        return result;
    }

    @Override
    public List<User> findLeastLoadedUsers(Integer[] tagIds, int roleId) {
        return userRepository.findLeastLoadedUsersByTagIdsAndRole(tagIds, tagIds.length, roleId);
    }

    @Override
    public User findOneLeastLoadedUser(Integer[] tagIds, int roleId) {
        return userRepository.findOneLeastLoadedUserByTagIdsAndRole(tagIds, tagIds.length, roleId);
    }

    @Override
    public long countByTagsAndRole(Integer[] tagIds, int roleId) {
        return userRepository.countByTagIdsAndRole(tagIds, tagIds.length, roleId);
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User createNew(User user) {
        if (userRepository.findByUsernameIgnoreCase(user.getUsername()) != null)
            throw new RuntimeException("User already created");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User originalUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
        if (originalUser == null)
            throw new UsernameNotFoundException("User not found");
        user.setId(originalUser.getId());
        user.setIsActive(originalUser.getIsActive());
        if (user.getPassword() == null)
            user.setPassword(originalUser.getPassword());
        else
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
