package com.innotechnum.practice.task1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Staff {
    private static Map<String, Department> departments = new LinkedHashMap<>();

    public static void transferCombinationWithIncrease(String file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Department department1 : departments.values()) {
                for (Department department2 : departments.values()) {
                    if ((department1 == department2) || (department1.getAverageSalary().compareTo(department2.getAverageSalary()) <= 0)) {
                        continue;
                    }
                    getCombinations(department1, department2, bufferedWriter);
                }
            }
        } catch (IOException e) {
            System.out.println("Некорректные параметры программы");
        }
    }

    private static void getCombinations(Department department1, Department department2,
                                        BufferedWriter bufferedWriter) throws IOException {
        Employee[] employees = department1.getEmployees().toArray(new Employee[department1.getEmployees().size()]);
        Deque<Employee> employeesForTransfer = new ArrayDeque<>();
        transferCombinations(employees, -1, department1, department2, employeesForTransfer, bufferedWriter);
    }

    private static void transferCombinations(Employee[] employees, int last, Department outDepartment,
                                             Department inDepartment, Deque<Employee> employeesForTransfer,
                                             BufferedWriter bufferedWriter) throws IOException {
        if (!employeesForTransfer.isEmpty()) {
            IOEmployees.outputCombinationsInFile(outDepartment.getName(), outDepartment.getAverageSalary(),
                    outDepartment.getAverageSalaryWithoutEmployees(employeesForTransfer), inDepartment.getName(),
                    inDepartment.getAverageSalary(),
                    inDepartment.getAverageSalaryWithEmployees(employeesForTransfer), employeesForTransfer,
                    bufferedWriter);
        }
        for (int i = last + 1; i < employees.length; i++) {
            employeesForTransfer.addLast(employees[i]);
            transferCombinations(employees, i, outDepartment, inDepartment, employeesForTransfer, bufferedWriter);
            employeesForTransfer.pollLast();
        }
    }

    public static void summary() {
        for (Department department : departments.values()) {
            System.out.println("В отделе " + department.getName() + " сотрудников - " +
                    department.getEmployees().size() + " ,средняя зарплата = " + department.getAverageSalary());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Некорректные параметры программы. Один файл для ввода, один файл для вывода.");
            return;
        }

        if ((departments = IOEmployees.readStaff(args[0])).isEmpty()) {
            return;
        }

        summary();
        System.out.println();

        transferCombinationWithIncrease(args[1]);
    }
}
