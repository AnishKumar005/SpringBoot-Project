package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void saveEmployee(Employee employee) {

        Integer employeeId;

        do {

            employeeId = (int) (100000 + Math.random() * 900000);

        } while (employeeRepository.existsById(employeeId));

        employee.setEmployeeId(employeeId);

        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
    
    @Override
    public List<Object[]> getEmployeeCountByDepartment() {
        return employeeRepository.getEmployeeCountByDepartment();
    }
    

    @Override
    public List<Employee> searchEmployees(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }

        return employeeRepository
                .findByEmployeeNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                        keyword,
                        keyword
                );
        
        
    }
    
    @Override
    public long getEmployeeCount() {
        return employeeRepository.count();
    }

    @Override
    public long getDepartmentCount() {
        return employeeRepository.countDepartments();
    }

    @Override
    public List<Employee> getRecentEmployees() {
        return employeeRepository.findTop5ByOrderByJoiningDateDesc();
    }
}