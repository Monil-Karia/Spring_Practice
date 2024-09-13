package com.h2.new_h2.controller;

import com.h2.new_h2.exception.RecordNotFoundException;
import com.h2.new_h2.model.EmployeeEntity;
import com.h2.new_h2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @RequestMapping
    public String getAllEmployees(Model model){
        System.out.println("getAllEmployees");

        List<EmployeeEntity> list = service.getAllEmployees();

        model.addAttribute("employees",list);

        return "list-employees";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editEmployeeById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
        System.out.println("editEmployeeById" + id);
        if (id.isPresent()){
            EmployeeEntity entity = service.getEmployeeById(id.get());
            model.addAttribute("employee",entity);
        }else{
            model.addAttribute("employee" , new EmployeeEntity());
        }

        return "add-edit-employee";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
    {
        System.out.println("deleteEmployeeById" + id);
        service.deleteEmployeeById(id);
        return "redirect:/";
    }
    @RequestMapping(path = "/createEmployee", method = RequestMethod.POST)
    public String createOrUpdateEmployee(EmployeeEntity employee)
    {
        System.out.println("createOrUpdateEmployee ");
        service.createOrUpdateEmployee(employee);
        return "redirect:/";
    }
}
