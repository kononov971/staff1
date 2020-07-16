import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Staff {
    public static void main(String[] args)  {
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

        for(Employee employee : staff) {
            System.out.println(employee.getFullName() + " " + employee.getDepartment() + " " + employee.getSalary());
        }

    }
}
