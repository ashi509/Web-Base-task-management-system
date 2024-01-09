package WebBased.Task.Management.System.service;

import WebBased.Task.Management.System.entity.TaskEntity;
import WebBased.Task.Management.System.exception.GenericException;
import org.springframework.http.ResponseEntity;


public interface TaskService {
  ResponseEntity<?> createTask(TaskEntity taskEntity) throws GenericException;
  ResponseEntity<?> getAllTask() throws GenericException;
  ResponseEntity<?> getByIdTask(long  id) throws GenericException;
  ResponseEntity<?> updateTask(TaskEntity taskEntity,Long id) throws GenericException;
  ResponseEntity<?> deleteTask(long id) throws GenericException;

}
