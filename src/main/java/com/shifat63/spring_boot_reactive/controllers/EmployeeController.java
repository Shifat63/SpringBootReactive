package com.shifat63.spring_boot_reactive.controllers;

import com.shifat63.spring_boot_reactive.model.Employee;
import com.shifat63.spring_boot_reactive.services.service.EmployeeService;
import com.shifat63.spring_boot_reactive.services.service.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

// Author: Shifat63

@Slf4j
@Controller
public class EmployeeController {
    private EmployeeService employeeService;
    private ShowroomService showroomService;

    public EmployeeController(EmployeeService employeeService, ShowroomService showroomService) {
        this.employeeService = employeeService;
        this.showroomService = showroomService;
    }

    @RequestMapping({"/employee", "/employee/", "/employee/index"})
    public String getEmployeeIndex(Model model) throws Exception{
        log.info("start: getEmployeeIndex method of EmployeeController");
        model.addAttribute("employees", employeeService.findAll());
        log.info("end: getEmployeeIndex method of EmployeeController");
        return "employee/index";
    }

    @RequestMapping("/employee/view/{employeeId}")
    public String viewEmployee(@PathVariable("employeeId") String employeeId, Model model) throws Exception{
        log.info("start: viewEmployee method of EmployeeController");
        model.addAttribute("employee", employeeService.findById(employeeId));
        log.info("end: viewEmployee method of EmployeeController");
        return "employee/view";
    }

    @RequestMapping("/employee/add")
    public String addEmployeeGet(Model model) throws Exception{
        log.info("start: addEmployeeGet method of EmployeeController");
        model.addAttribute("employee", new Employee());
        model.addAttribute("showrooms", showroomService.findAll());
        log.info("end: addEmployeeGet method of EmployeeController");
        return "employee/add";
    }

    @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
    public String addOrEditEmployeePost(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model) throws Exception
    {
        log.info("start: addOrEditEmployeePost method of EmployeeController");

        if(bindingResult.hasErrors()){
            log.error("error: addOrEditEmployeePost method of EmployeeController -> Employee model validation failed");
            model.addAttribute("showrooms", showroomService.findAll());
            return "employee/add";
        }

        employeeService.saveOrUpdate(employee);
        log.info("end: addOrEditEmployeePost method of EmployeeController");
        return "redirect:/employee/index";
    }

    @RequestMapping("employee/edit/{employeeId}")
    public String editEmployeeGet(@PathVariable("employeeId") String employeeId, Model model) throws Exception
    {
        log.info("start: editEmployeeGet method of EmployeeController");
        model.addAttribute("employee", employeeService.findById(employeeId));
        model.addAttribute("showrooms", showroomService.findAll());
        log.info("end: editEmployeeGet method of EmployeeController");
        return "employee/add";
    }

    @RequestMapping("/employee/delete/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") String employeeId) throws Exception
    {
        log.info("start: deleteEmployee method of EmployeeController");
        employeeService.deleteById(employeeId);
        log.info("end: deleteEmployee method of EmployeeController");
        return "redirect:/employee/index";
    }
}

