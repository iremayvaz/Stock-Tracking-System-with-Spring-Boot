package model.entity;

public class User {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private Employee employee;

    public void attachEmployee(Employee emp) {
        this.employee = emp;
        if (emp != null) emp.setUser(this);
    }
    public void detachEmployee() {
        if (this.employee != null) this.employee.setUser(null);
        this.employee = null;
    }
}
