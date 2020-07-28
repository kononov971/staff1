package com.innotechnum.practice.task1;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;


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

    public static void outputCombinationsInFile(String outDepartmentName, BigDecimal outDepartmentAverageSalary,
                                                BigDecimal outDepartmentAverageSalaryWithoutEmployees,
                                                String inDepartmentName, BigDecimal inDepartmentAverageSalary,
                                                BigDecimal inDepartmentAverageSalaryWithEmployees,
                                                Queue<Employee> employeesForTransfer, BufferedWriter bufferedWriter) throws IOException {
        if ((Department.getAverageSalary(employeesForTransfer).compareTo(outDepartmentAverageSalary) < 0) &&
                (Department.getAverageSalary(employeesForTransfer).compareTo(inDepartmentAverageSalary) > 0)) {
            bufferedWriter.write("    Сотрудники: ");
            for (Employee e : employeesForTransfer) {
                bufferedWriter.write(e.getFullName() + ", ");
            }
            bufferedWriter.write("из отдела " + outDepartmentName + " в отдел " + inDepartmentName + "." + "\n");
            bufferedWriter.write("    Средняя зарплата в отделе " + outDepartmentName + " изменится с " +
                    outDepartmentAverageSalary + " на " + outDepartmentAverageSalaryWithoutEmployees + "\n");
            bufferedWriter.write("    Средняя зарплата в отделе " + inDepartmentName + " изменится с " +
                    inDepartmentAverageSalary + " на " + inDepartmentAverageSalaryWithEmployees + "\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        }
    }
}
