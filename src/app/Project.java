/*
 * class Project
 *
 *  class Project stores information about its id
 *  and employees which are working on this project.
 *
 * 3/22/2020
 *
 * @author VeselinaTencheva
 */
package app;
import java.util.*;

public class Project {
    // id of the project
    private String id;
    //employees which are working on this project
    private Set<Employee> employees;

    public Project(String id) {
        this.id = id;
        employees = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", employees=" + employees +
                '}';
    }
}
