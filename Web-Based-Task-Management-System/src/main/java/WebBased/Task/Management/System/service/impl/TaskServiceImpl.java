package WebBased.Task.Management.System.service.impl;

import WebBased.Task.Management.System.entity.TaskEntity;
import WebBased.Task.Management.System.exception.GenericException;
import WebBased.Task.Management.System.repository.TaskRepository;
import WebBased.Task.Management.System.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public ResponseEntity<?> createTask(TaskEntity taskEntity) throws GenericException {
        taskEntity.setId(0);
        taskEntity.setCreateDate(new Date());
        var task=taskRepository.save(taskEntity);
        if(task==null)
            throw new GenericException(HttpStatus.NOT_FOUND.value(),
                    "Something went wrong task not save.");
            return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<?> getAllTask() throws GenericException {
        var task=taskRepository.findAll();
        if(task==null)
            throw new GenericException(HttpStatus.NOT_FOUND.value(),
                    "Something went wrong task not Found.");
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<?> getByIdTask(long id) throws GenericException {
    var task=taskRepository.findById(id).orElseThrow(()->new
            GenericException(HttpStatus.NOT_FOUND.value(),
            "Task not found with given id "+id));
    return ResponseEntity.ok(task);
    }
    @Override
    public ResponseEntity<?> updateTask(TaskEntity taskEntity, Long id) throws GenericException {
        var task=taskRepository.findById(id).orElseThrow(()->new
                GenericException(HttpStatus.NOT_FOUND.value(),
                "Task not found with given id "+id));
        taskEntity.setId(id);
        taskEntity.setCreateDate(new Date());
        task=taskRepository.save(taskEntity);
        if(task==null)
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Task not save.");
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<?> deleteTask(long id) throws GenericException {
        var task=taskRepository.findById(id).orElseThrow(()->new
                GenericException(HttpStatus.NOT_FOUND.value(),
                "Task not found with given id "+id));
        taskRepository.deleteById(id);
        return ResponseEntity.ok(task);
    }
}
