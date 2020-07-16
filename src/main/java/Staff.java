import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Staff {
    public static void main(String[] args)  {
        Map<String, Department> departments = new LinkedHashMap<>();
        List<Employee> staff = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Staff.txt"));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                staff.add(new Employee(currentLine));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Employee employee : staff) {
            Department department;
            if ((department = departments.get(employee.getDepartment())) != null) {
                department.newEmployee(employee.getSalary());
            } else {
                department = new Department(employee.getDepartment(), employee.getSalary());
                departments.put(employee.getDepartment(), department);
            }
        }

        for(Employee employee : staff) {
            System.out.println(employee.getFullName() + " " + employee.getDepartment() + " " + employee.getSalary());
        }

        for(Map.Entry<String, Department> entry : departments.entrySet()) {
            System.out.println("key = " + entry.getKey() + " name = " + entry.getValue().getName() +
                    " totalSalary = " + entry.getValue().getTotalSalary() +
                    " numberEmployee = " + entry.getValue().getNumberEmployee() +
                    " averageSalary = " + entry.getValue().getAverageSalary());
        }

    }
}
