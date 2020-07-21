package com.innotechnum.practice.task1;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Staff {
    private static Map<String, Department> departments = new LinkedHashMap<>();

    public static Map<String, Department> readStaff(String file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            String fullName;
            Department department;
            BigDecimal salary;
            Employee employee;

            while ((currentLine = bufferedReader.readLine()) != null) {
                if (!checkEmployee(currentLine)) {
                    continue;
                }
                String[] information = currentLine.split("/");

                fullName = information[0];
                department = departments.getOrDefault(information[1], new Department(information[1]));
                departments.putIfAbsent(information[1], department);
                salary = new BigDecimal(information[2]);

                employee = new Employee(fullName, department, salary);
                department.newEmployee(employee);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл " + file + " не найден");
            throw new FileNotFoundException();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла " + file);
            throw new IOException();
        }
        return departments;
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
            if ((salary.compareTo(BigDecimal.ZERO) < 0) || (salary.scale() > 2)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректная зарплата у сотрудника - " + information[0]);
            return false;
        }

        return true;
    }

    public static void transferWithIncrease() {
        System.out.println("Варианты переводов сотрудников, при которых средняя зарплата увеличивается в обоих отделах:");
        for (Department department1 : departments.values()) {
            for (Department department2 : departments.values()) {
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
        }
    }

    public static void summary() {
        for (Department department : departments.values()) {
            System.out.println("В отделе " + department.getName() + " сотрудников - " + department.getEmployees().size() +
                    " ,средняя зарплата = " + department.getAverageSalary());
        }
    }

    public static void main(String[] args) {
        try {
            readStaff(args[0]);


            summary();
            System.out.println();

            transferWithIncrease();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Некорректные параметры программы");
        } catch (IOException e) {

        }
    }
}
