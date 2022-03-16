package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

    User findByUsernameIgnoreCaseAndIsActiveIsTrue(String username);

    @Query(value = "select us.* from at.users us " +
            "where ( " +
            "    select count(*) from at.users_tags " +
            "    where user_id = us.id " +
            "      and tag_id in ?1 " +
            "    ) = ?2 " +
            "and ( " +
            "    select count(*) from at.users_roles " +
            "    where user_id = us.id " +
            "      and role_id = ?3 " +
            "    ) > 0 " +
            "and us.is_active " +
            "order by ( " +
            "    select count(*) from at.tasks " +
            "    where assigned_user_id = us.id " +
            "    and status in ('OPEN', 'IN_PROGRESS') " +
            "             ), " +
            "         ( " +
            "    select count(*) from at.tasks " +
            "    where assigned_user_id = us.id " +
            "    and status in ('IN_PROGRESS') " +
            "             )", nativeQuery = true)
    List<User> findLeastLoadedUsersByTagIdsAndRole(Integer[] tagIds, int tagsNum, int roleId);

}
