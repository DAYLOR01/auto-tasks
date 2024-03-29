package kz.autotask.web.service;

import kz.autotask.web.data.entity.Tag;
import kz.autotask.web.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findActiveUserByUsername(String username);

    User findActiveByUsernameAndPassword(String username, String password);

    void changePassword(String username, String newPassword);

    void changeActive(String username, boolean isActive);

    List<Tag> findMainTagsByUsername(String username);

    List<User> findLeastLoadedUsers(Integer[] tagIds, int roleId);

    User findOneLeastLoadedUser(Integer[] tagIds, int roleId);

    long countByTagsAndRole(Integer[] tagIds, int roleId);

    Page<User> getPage(Pageable pageable);

    User createNew(User user);

    User updateUser(User user);

}
