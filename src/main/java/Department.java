public class Department {
    private String name;
    private int totalSalary;
    private int numberEmployee;

    public Department(String name) {
        this.name = name;
        this.totalSalary = 0;
        this.numberEmployee = 0;
    }

    public Department(String name, int salary) {
        this.name = name;
        this.totalSalary = salary;
        this.numberEmployee = 1;
    }


    public String getName() {
        return name;
    }

    public int getTotalSalary() {
        return totalSalary;
    }

    public int getNumberEmployee() {
        return numberEmployee;
    }

    public void newEmployee(int salary) {
        this.totalSalary += salary;
        this.numberEmployee++;
    }

    public int getAverageSalary() {
        return totalSalary / numberEmployee;
    }

}
