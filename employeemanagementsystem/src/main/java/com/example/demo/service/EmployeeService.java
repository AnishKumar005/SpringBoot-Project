package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Employee;

public interface EmployeeService {

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id);

    void updateEmployee(Employee employee);

    void deleteEmployee(Integer id);

    List<Employee> searchEmployees(String keyword);
    
    long getEmployeeCount();

    long getDepartmentCount();

    List<Employee> getRecentEmployees();
    
    List<Object[]> getEmployeeCountByDepartment();
}