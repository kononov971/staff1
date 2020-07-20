package com.innotechnum.practice.task1;

import java.math.BigDecimal;

public class Employee {
    private String fullName;
    private Department department;
    private BigDecimal salary;

    public Employee(String fullName, Department department, BigDecimal salary) {
        this.fullName = fullName;
        this.department = department;
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public Department getDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
