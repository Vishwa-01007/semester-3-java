public class F2EmployeeInheritance {

    static class Employee {
        private int empId;
        private String empName;
        private double salary;

        Employee(int empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        double getSalary() {
            return salary;
        }

        String getEmpName() {
            return empName;
        }
    }

    static class ManagerEmployee extends Employee {
        private double teamBonus;

        ManagerEmployee(
                int empId,
                String empName,
                double salary,
                double teamBonus) {

            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }

        double getTeamBonus() {
            return teamBonus;
        }
    }

    static class InternEmployee extends Employee {
        private double stipendCap;

        InternEmployee(
                int empId,
                String empName,
                double salary,
                double stipendCap) {

            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }

        double getStipendCap() {
            return stipendCap;
        }
    }

    public static void main(String[] args) {

        Employee[] employees = {
                new Employee(101, "Arun", 40000),
                new ManagerEmployee(102, "Ravi", 70000, 8000),
                new InternEmployee(103, "Kiran", 12000, 10000)
        };

        for (Employee employee : employees) {

            if (employee instanceof ManagerEmployee) {

                ManagerEmployee manager =
                        (ManagerEmployee) employee;

                System.out.println(
                        "Manager effective pay: Rs " +
                                manager.effectiveSalary()
                );

            } else if (employee instanceof InternEmployee) {

                InternEmployee intern =
                        (InternEmployee) employee;

                System.out.println(
                        "Intern effective pay: Rs " +
                                intern.effectiveSalary()
                );

            } else {

                System.out.println(
                        "Plain employee pay: Rs " +
                                employee.getSalary()
                );
            }
        }
    }
}