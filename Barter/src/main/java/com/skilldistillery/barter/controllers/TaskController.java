package com.skilldistillery.barter.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.barter.entities.Task;
import com.skilldistillery.barter.services.TaskService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost/"})
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	//all tasks (for admin purposes)
	@GetMapping(path="tasks")
	List<Task> getAllTasks(){
		return taskService.getAllTasks();
	}
	
	//add task
	@PostMapping(path="user/{id}/tasks")
	Task createTask(@RequestBody Task task, @PathVariable int id,HttpServletRequest req,HttpServletResponse res) {
		try {
			task = taskService.createTask(task, id);
			res.setStatus(201);
			//StringBuffer url = req.getRequestURL(); // define as stringbuffer
			//url.append("/").append(task.getId()); // append id to url so will show user the post url
			//res.setHeader("Location", "http://localhost:8083"); // location
		}
		catch(Exception e) {
			e.printStackTrace();
			res.setStatus(404);
			task=null;
		}
		return task;
	}
	
}