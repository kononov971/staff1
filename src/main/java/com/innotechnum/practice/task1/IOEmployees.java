package com.innotechnum.practice.task1;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class IOEmployees {
    public static Map<String, Department> readStaff(String file) {
        Map<String, Department> departments = new LinkedHashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            String fullName;
            Department department;
            BigDecimal salary;
            Employee employee;

            while ((currentLine = bufferedReader.readLine()) != null) {
                if (!Employee.checkEmployee(currentLine)) {
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
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла " + file);
        }
        return departments;
    }

    public static void outputCombinationsInConsole(Department outDepartment, Department inDepartment, List<Employee> employeesForTransfer) {
        if ((outDepartment.getAverageSalaryWithoutEmployees(employeesForTransfer).compareTo(outDepartment.getAverageSalary()) > 0) &&
                (inDepartment.getAverageSalaryWithEmployees(employeesForTransfer).compareTo(inDepartment.getAverageSalary()) > 0)) {
            System.out.print("    Сотрудники: ");
            for (Employee e : employeesForTransfer) {
                System.out.print(e.getFullName() + ", ");
            }
            System.out.println("из отдела " + outDepartment.getName() + " в отдел " + inDepartment.getName() + ".");
            System.out.println("    Средняя зарплата в отделе " + outDepartment.getName() + " изменится с " + outDepartment.getAverageSalary() +
                    " на " + outDepartment.getAverageSalaryWithoutEmployees(employeesForTransfer));
            System.out.println("    Средняя зарплата в отделе " + inDepartment.getName() + " изменится с " + inDepartment.getAverageSalary() +
                    " на " + inDepartment.getAverageSalaryWithEmployees(employeesForTransfer));
            System.out.println();
        }
    }

    public static void outputCombinationsInFile(Department outDepartment, Department inDepartment, List<Employee> employeesForTransfer, String file) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            if ((outDepartment.getAverageSalaryWithoutEmployees(employeesForTransfer).compareTo(outDepartment.getAverageSalary()) > 0) &&
                    (inDepartment.getAverageSalaryWithEmployees(employeesForTransfer).compareTo(inDepartment.getAverageSalary()) > 0)) {
                bufferedWriter.write("    Сотрудники: ");
                for (Employee e : employeesForTransfer) {
                    bufferedWriter.write(e.getFullName() + ", ");
                }
                bufferedWriter.write("из отдела " + outDepartment.getName() + " в отдел " + inDepartment.getName() + "." + "\n");
                bufferedWriter.write("    Средняя зарплата в отделе " + outDepartment.getName() + " изменится с " + outDepartment.getAverageSalary() +
                        " на " + outDepartment.getAverageSalaryWithoutEmployees(employeesForTransfer) + "\n");
                bufferedWriter.write("    Средняя зарплата в отделе " + inDepartment.getName() + " изменится с " + inDepartment.getAverageSalary() +
                        " на " + inDepartment.getAverageSalaryWithEmployees(employeesForTransfer) + "\n");
                bufferedWriter.write("\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            System.out.println("Некорректные параметры программы");
        }
    }

    public static void clearFile(String file) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
