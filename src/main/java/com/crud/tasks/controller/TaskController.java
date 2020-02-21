package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/task")
@RestController
public class TaskController {
    private DbService service;
    private TaskMapper taskMapper;

    @Autowired
    public void setService(DbService service) {
        this.service = service;
    }

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }


    @RequestMapping(method = RequestMethod.GET, value = "getTaskById")
    public TaskDto getTaskById(@RequestParam long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.getTaskById(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "deleteTask")
    public void delete(@RequestParam long taskId) {
        taskMapper.mapToTaskDto(service.deleteTask(taskId));
    }*/

    /*@RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(Long taskId) {
        return new TaskDto(1L, "Test title", "Test_content");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(Long taskId) {
    }

    } */
}