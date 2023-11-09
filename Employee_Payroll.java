import java.io.*;
import java.util.ArrayList;

class Employee {
    private String name;
    private String designation;
    private double salary;

    public Employee(String name, String designation, double salary) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public double getSalary() {
        return salary;
    }

    public void calculatePayroll() {
        // Calculate payroll logic specific to each employee type
    }

    public void displayPayroll() {
        System.out.println("Employee Name: " + name);
        System.out.println("Designation: " + designation);
        System.out.println("Monthly Salary: $" + salary);
    }
}

class FullTimeEmployee extends Employee {
    private double bonus;

    public FullTimeEmployee(String name, String designation, double salary, double bonus) {
        super(name, designation, salary);
        this.bonus = bonus;
    }

    @Override
    public void calculatePayroll() {
        setSalary(getSalary() + bonus);
    }

    @Override
    public void displayPayroll() {
        super.displayPayroll();
        System.out.println("Bonus: $" + bonus);
        System.out.println("Total Monthly Salary: $" + getSalary());
        System.out.println();
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String name, String designation, double hourlyRate, int hoursWorked) {
        super(name, designation, 0); // Part-time employees' initial salary is 0
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public void calculatePayroll() {
        setSalary(hourlyRate * hoursWorked);
    }

    @Override
    public void displayPayroll() {
        super.displayPayroll();
        System.out.println("Hourly Rate: $" + hourlyRate);
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Total Monthly Salary: $" + getSalary());
        System.out.println();
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();

        // Load employee data from a file (if available)
        loadEmployeeData(employees);

        if (employees.isEmpty()) {
            System.out.println("No employees found. Please add employees to the system.");
        } else {
            for (Employee employee : employees) {
                employee.calculatePayroll();
                employee.displayPayroll();
            }
        }

        // Save employee data to a file for future use
        saveEmployeeData(employees);
    }

    private static void loadEmployeeData(ArrayList<Employee> employees) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employeeData.ser"))) {
            employees.addAll((ArrayList<Employee>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading employee data. Initializing with an empty employee list.");
        }
    }

    private static void saveEmployeeData(ArrayList<Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employeeData.ser"))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving employee data.");
        }
    }
}
