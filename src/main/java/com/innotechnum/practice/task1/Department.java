package com.innotechnum.practice.task1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.Queue;
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

    public BigDecimal getAverageSalaryWithoutEmployees(Queue<Employee> transferEmployees) {
        BigDecimal potentialTotalSalary = this.getTotalSalary();
        for(Employee employee : transferEmployees) {
            potentialTotalSalary = potentialTotalSalary.subtract(employee.getSalary());
        }
        if (employees.size() - transferEmployees.size() != 0) {
            return potentialTotalSalary.divide(BigDecimal.valueOf(employees.size() - transferEmployees.size()), 2,
                    RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getAverageSalaryWithEmployees(Queue<Employee> transferEmployees) {
        BigDecimal potentialTotalSalary = this.getTotalSalary();
        for(Employee employee : transferEmployees) {
            potentialTotalSalary = potentialTotalSalary.add(employee.getSalary());
        }
        return potentialTotalSalary.divide(BigDecimal.valueOf(employees.size() + transferEmployees.size()), 2,
                RoundingMode.HALF_UP);
    }

    public static BigDecimal getAverageSalary(Queue<Employee> combinationOfEmployees) {
//        if (combinationOfEmployees.isEmpty()) {
//            return BigDecimal.ZERO;
//        }
        BigDecimal totalSalary = BigDecimal.ZERO;
        for(Employee employee : combinationOfEmployees) {
            totalSalary = totalSalary.add(employee.getSalary());
        }
        return totalSalary.divide(BigDecimal.valueOf(combinationOfEmployees.size()), 2, RoundingMode.HALF_UP);
    }

}
