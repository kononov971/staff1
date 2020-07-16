public class Employee {
    private String fullName;
    private String department;
    private int salary;

    public Employee(String line) {
        String[] employee = line.split("/");
        this.fullName = employee[0];
        this.department = employee[1];
        this.salary = Integer.parseInt(employee[2]);
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

}
