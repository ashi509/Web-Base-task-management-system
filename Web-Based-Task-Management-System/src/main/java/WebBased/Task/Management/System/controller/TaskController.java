package WebBased.Task.Management.System.controller;

import WebBased.Task.Management.System.entity.TaskEntity;
import WebBased.Task.Management.System.exception.GenericException;
import WebBased.Task.Management.System.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping
    ResponseEntity<?> createTask(@RequestBody TaskEntity taskEntity) throws GenericException{
        return taskService.createTask(taskEntity);
    }
    @GetMapping
    ResponseEntity<?> getAllTask() throws GenericException{
        return taskService.getAllTask();
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getByIdTask(@PathVariable long  id) throws GenericException{
        return taskService.getByIdTask(id);
    }
    @PutMapping("{id}")
    ResponseEntity<?> updateTask(@RequestBody TaskEntity taskEntity,@PathVariable Long id) throws GenericException{
        return taskService.updateTask(taskEntity,id);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable long id) throws GenericException{
        return taskService.deleteTask(id);


    }


}
