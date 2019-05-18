package co.yiiu.pydeploy.repository;

import co.yiiu.pydeploy.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
