package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.TaskHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    List<TaskHistory> findAllByTask_Id(long taskId, Sort sort);

}
