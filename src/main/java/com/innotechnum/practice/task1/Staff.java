package com.innotechnum.practice.task1;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Staff {
    private static Map<String, Department> departments = new LinkedHashMap<>();
    private static List<Employee> staff = new ArrayList<>();

    public static void readStaff(String file) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            String fullName;
            Department department;
            BigDecimal salary = null;
            Employee employee;

            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] information = currentLine.split("/");
                if (information.length != 3) {
                    System.out.println("В строке - " + currentLine + " некорректное количество разделителей");
                    continue;
                }
                fullName = information[0];

                department = getDepartment(information[1]);

                try {
                    salary = new BigDecimal(information[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Некорректная зарплата у сотрудника - " + fullName);
                    continue;
                }

                employee = new Employee(fullName, department, salary);
                staff.add(employee);
                department.newEmployee(employee);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
            System.exit(0);
        }
    }

    public static Department getDepartment(String name) {
        Department department;
        if ((department = departments.get(name)) == null) {
            department = new Department(name);
            departments.put(name, department);
        }
        return department;
    }

    public static void writeStaff(String file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for(Employee employee : staff) {
                bufferedWriter.write(employee.getFullName() + "/" + employee.getDepartment().getName() + "/" + employee.getSalary() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transfer(Employee employee, Department department) {
        if (employee.getDepartment() == department) {
            System.out.println("Перевод сотрудника в его текущий отдел не возможен");
            return;
        }
        Department currentDepartment = employee.getDepartment();
        employee.setDepartment(department);
        currentDepartment.removeEmployee(employee);
        department.newEmployee(employee);
        if (!departments.containsValue(department)) {
            departments.put(department.getName(), department);
        }
    }

    public static Employee transferWithIncrease(Department department1, Department department2) {
        if (department1 == department2) {
            System.out.println("Перевод сотрудника в его текущий отдел не возможен");
            return null;
        }
        Employee employee;
        if (department1.getAverageSalary().compareTo(department2.getAverageSalary()) == 0)  {
            System.out.println("Средние зарплаты отделов равны");
            return null;
        } else if  (department1.getAverageSalary().compareTo(department2.getAverageSalary()) == 1) {
            if (department1.getNumberEmployee() <= 1) {
                System.out.println("В отделе " + department1.getName() + " недостаточно сотрудников");
                return null;
            }
            employee = searchEmployeeForTransfer(department1, department2);
            transfer(employee, department2);
            return employee;
        } else {
            if (department2.getNumberEmployee() <= 1) {
                System.out.println("В отделе " + department2.getName() + " недостаточно сотрудников");
                return null;
            }
            employee = searchEmployeeForTransfer(department2, department1);
            transfer(employee, department1);
            return employee;
        }
    }

    public static Employee searchEmployeeForTransfer(Department departmentWithHigherAverageSalary, Department departmentWithLoverAverageSalary) {
        for(Employee employee : departmentWithHigherAverageSalary.getEmployees()) {
            if ((employee.getSalary().compareTo(departmentWithHigherAverageSalary.getAverageSalary()) == -1) &&
                    (employee.getSalary().compareTo(departmentWithLoverAverageSalary.getAverageSalary()) == 1)) {
                return employee;
            }
        }
        return null;
    }

    public static void summary() {
        for(Map.Entry<String, Department> entry : departments.entrySet()) {
            System.out.println("В отделе " + entry.getValue().getName() + " сотрудников - " + entry.getValue().getNumberEmployee() +
                    " ,средняя зарплата = " + entry.getValue().getAverageSalary());
        }
    }

    public static void main(String[] args)  {
        readStaff(args[0]);

        for(Employee employee : staff) {
            System.out.println(employee.getFullName() + " " + employee.getDepartment().getName() + " " + employee.getSalary());
        }

        summary();
        System.out.println();

        transferWithIncrease(departments.get("Бухгалтерия"), departments.get("Маркетинг"));
        writeStaff(args[1]);

        summary();
    }
}
