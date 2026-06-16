package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee>
    findByEmployeeNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String employeeName,
            String department
    );
    
    @Query("""
    		SELECT e.department, COUNT(e)
    		FROM Employee e
    		GROUP BY e.department
    		""")
    		List<Object[]> getEmployeeCountByDepartment();

    @Query("""
           SELECT COUNT(DISTINCT e.department)
           FROM Employee e
           """)
    long countDepartments();

    List<Employee> findTop5ByOrderByJoiningDateDesc();
}