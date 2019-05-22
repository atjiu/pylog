package co.yiiu.pydeploy.service;

import co.yiiu.pydeploy.model.Task;
import co.yiiu.pydeploy.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "lastDeployTime"));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
