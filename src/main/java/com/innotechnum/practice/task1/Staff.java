package com.innotechnum.practice.task1;

import java.util.*;

public class Staff {
    private static Map<String, Department> departments = new LinkedHashMap<>();

    public static void transferWithIncrease() {
        System.out.println("Варианты переводов сотрудников, при которых средняя зарплата увеличивается в обоих отделах:");
        for (Department department1 : departments.values()) {
            for (Department department2 : departments.values()) {
                transferOptions(department1, department2);

            }
        }
    }

    private static void transferOptions(Department department1, Department department2) {
        if (department1.getAverageSalary().compareTo(department2.getAverageSalary()) > 0) {
            for (Employee employee : department1.getEmployees()) {
                if ((employee.getSalary().compareTo(department1.getAverageSalary()) < 0) &&
                        (employee.getSalary().compareTo(department2.getAverageSalary()) > 0)) {
                    System.out.println("    " + employee.getFullName() + " из отдела " + department1.getName() +
                            " в отдел " + department2.getName());
                }
            }
        }
    }


    public static void transferCombinationWithIncrease(String file) {
        IOEmployees.clearFile(file);
        for (Department department1 : departments.values()) {
            for (Department department2 : departments.values()) {
                getCombinations(department1, department2, file);
            }
        }
    }

    private static void getCombinations(Department department1, Department department2, String file) {
        Employee[] employees = department1.getEmployees().toArray(new Employee[department1.getEmployees().size()]);
        Stack<Employee> employeesForTransfer = new Stack<>();
        transferCombinations(employees, -1, department1, department2, employeesForTransfer, file);
    }

    private static void transferCombinations(Employee[] employees, int last, Department outDepartment, Department inDepartment, Stack<Employee> employeesForTransfer, String file) {
        //IOEmployees.outputCombinationsInConsole(outDepartment, inDepartment, employeesForTransfer);
        IOEmployees.outputCombinationsInFile(outDepartment, inDepartment, employeesForTransfer, file);
        for (int i = last + 1; i < employees.length; i++) {
            employeesForTransfer.push(employees[i]);
            transferCombinations(employees, i, outDepartment, inDepartment, employeesForTransfer, file);
            employeesForTransfer.pop();
        }
    }

    public static void summary() {
        for (Department department : departments.values()) {
            System.out.println("В отделе " + department.getName() + " сотрудников - " + department.getEmployees().size() +
                    " ,средняя зарплата = " + department.getAverageSalary());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Некорректные параметры программы");
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
