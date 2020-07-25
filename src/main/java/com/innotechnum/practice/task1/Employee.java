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

    public static boolean checkEmployee(String line) {
        String[] information = line.split("/");
        if (information.length != 3) {
            System.out.println("В строке - " + line + " некорректное количество разделителей");
            return false;
        }

        if (information[0].isEmpty()) {
            System.out.println("Некорректное имя пользователя в строке - " + information);
            return false;
        }

        if (information[1].isEmpty()) {
            System.out.println("Некорректное название отдела в строке - " + information);
            return false;
        }

        try {
            BigDecimal salary = new BigDecimal(information[2]);
            if ((salary.compareTo(BigDecimal.ZERO) <= 0) || (salary.scale() > 2)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректная зарплата у сотрудника - " + information[0]);
            return false;
        }

        return true;
    }
}
