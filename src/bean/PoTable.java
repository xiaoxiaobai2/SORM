package bean;

public class PoTable {
    //SELECT emp.id,emp.`name`,emp.salary,emp.department,student.ID FROM emp
    //JOIN student on emp.`name`=student.`Name`
    private Integer empId;
    private String name;
    private Double salary;
    private String department;
    private Integer stuID;

    public PoTable() {
    }

    public PoTable(Integer empId, String name, Double salary, String department, Integer stuID) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.stuID = stuID;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getStuId() {
        return stuID;
    }

    public void setStuID(Integer stuId) {
        this.stuID = stuId;
    }
}
