package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "employeeCount",
                employeeService.getEmployeeCount());

        model.addAttribute(
                "departmentCount",
                employeeService.getDepartmentCount());

        model.addAttribute(
                "recentEmployees",
                employeeService.getRecentEmployees());

        return "home";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {

        model.addAttribute("employee", new Employee());

        return "addEmployee";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "addEmployee";
        }

        employeeService.saveEmployee(employee);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee added successfully.");

        return "redirect:/employee/list";
    }
    
    @GetMapping("/analytics")
    public String analytics(Model model) {

        model.addAttribute("employeeCount",
                employeeService.getEmployeeCount());

        model.addAttribute("departmentCount",
                employeeService.getDepartmentCount());

        model.addAttribute("departmentData",
                employeeService.getEmployeeCountByDepartment());

        model.addAttribute("recentEmployees",
                employeeService.getRecentEmployees());

        model.addAttribute("performance", 98);

        model.addAttribute("growthRate", 24);

        return "analytics";
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {

        model.addAttribute(
                "employees",
                employeeService.getAllEmployees());

        model.addAttribute(
                "employeeCount",
                employeeService.getEmployeeCount());

        return "employeeList";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(
            @PathVariable Integer id,
            Model model) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            return "redirect:/employee/list";
        }

        model.addAttribute("employee", employee);

        return "employeeDetails";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Integer id,
            Model model) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            return "redirect:/employee/list";
        }

        model.addAttribute("employee", employee);

        return "editEmployee";
    }

    @PostMapping("/update")
    public String updateEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "editEmployee";
        }

        employeeService.updateEmployee(employee);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee updated successfully.");

        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        employeeService.deleteEmployee(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Employee deleted successfully.");

        return "redirect:/employee/list";
    }

    @GetMapping("/search")
    public String searchEmployees(
            @RequestParam(value = "keyword", required = false)
            String keyword,
            Model model) {

        List<Employee> employees;

        if (keyword == null || keyword.trim().isEmpty()) {

            employees =
                    employeeService.getAllEmployees();

        } else {

            employees =
                    employeeService.searchEmployees(keyword);
        }

        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);

        return "employeeList";
    }
}