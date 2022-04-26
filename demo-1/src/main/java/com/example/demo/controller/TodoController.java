package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoRepository;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import javax.validation.Valid;
import java.util.Date;

@CrossOrigin(maxAge=3600)
@Controller
public class TodoController {

    //@Autowired
    //TodoService todoService;

    @Autowired
    TodoRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping(value = "/list-todos", method=RequestMethod.GET)
    public String showListTodos(ModelMap model)
    {
        String name = getLoggedInUserName(model);
        //model.put("todos", todoService.retrieveTodos(name));
        model.put("todos", repository.findByUser(name));
        return "list-todos";
    }

    private String getLoggedInUserName(ModelMap model) {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    @RequestMapping(value = "/add-todo", method=RequestMethod.GET)
    public String showAddTodoPage(ModelMap model)
    {
        model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "", new Date(), false));
        return "addTodo";
    }

    @RequestMapping(value = "/delete-todo", method=RequestMethod.GET)
    public String deleteTodo(@RequestParam int id)
    {
        repository.deleteById(id);
        //todoService.deleteTodo(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "/update-todo", method=RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model)
    {
        //Todo todo = todoService.retrieveTodo(id);
        Todo todo = repository.getById(id);
        model.put("todo", todo);
        return "updateTodo";
    }

    @RequestMapping(value = "/update-todo", method=RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result)
    {
        if(result.hasErrors()) {
            return "updateTodo";
        }
        todo.setUser(getLoggedInUserName(model));

        repository.save(todo);
        //todoService.updateTodo(todo);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/add-todo", method=RequestMethod.POST)
    public String addTodos(ModelMap model, @Valid Todo todo, BindingResult result)
    {
        if(result.hasErrors()) {
            return "todo";
        }
        todo.setUser(getLoggedInUserName(model));
        repository.save(todo);
        //todoService.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
        return "redirect:/list-todos";
    }

}
