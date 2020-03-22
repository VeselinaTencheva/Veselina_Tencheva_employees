/*
 * class Employee
 *
 * The Employee class stores information about the employee's id
 * and dates from which he is working on a project
 *
 * 3/22/2020
 *
 * @author VeselinaTencheva
 */
package app;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Employee {
    // id of the employee
    private String employeeId;
    //the date on which the employee has started working on a project
    private Date dateFrom;
    //the date on which the employee has stopped working on a project
    private Date dateTo;

    public Employee( ) {

    }

    public Employee(String employeeId, String dateFrom, String dateTo) throws ParseException {
        this.employeeId = employeeId;
        setDateFrom(dateFrom);
        setDateTo((dateTo));
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public Date getDateFrom() {
        return dateFrom;
    }

    // this method converts the given String to type Date
    public void setDateFrom(String dateFrom) throws ParseException {
        this.dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
    }

    public Date getDateTo() {
        return dateTo;
    }

    // this method converts the given String to type Date. It can throw exception if the String is not parsable to Date
    // and the solution is to set this date to current date.
    public void setDateTo(String dateTo) throws ParseException {
        try
        {
            this.dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);
        }catch (ParseException ex){
            this.dateTo = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(new Date().toString());
        }
    }

    @Override
    public String toString() {
        return employeeId;
    }
}
