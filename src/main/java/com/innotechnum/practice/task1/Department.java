package com.innotechnum.practice.task1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.Set;

public class Department {
    private String name;
    private Set<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new LinkedHashSet<>();
    }


    public String getName() {
        return name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public BigDecimal getTotalSalary() {
        BigDecimal totalSalary = new BigDecimal(0);
        for(Employee employee : employees) {
            totalSalary = totalSalary.add(employee.getSalary());
        }
        return totalSalary;
    }

    public void newEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    public BigDecimal getAverageSalary() {
        return getTotalSalary().divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }

}
