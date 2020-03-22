/*
 * class Application
 *
 * The application class reads information from files
 *  and stores that information in different types of data structures.
 *
 * 3/22/2020
 *
 * @author VeselinaTencheva
 */
package app;
import java.io.*;
import java.text.ParseException;
import java.util.*;

public class Application implements Runnable {

    /*
    * EXPRESSION_TO_DAYS is expression which is calculated by 1000*60*60*24.
    * This is how we convert from milliseconds to days
     */
    private final long  EXPRESSION_TO_DAYS=86400000;

    /*
    * the set is being used because we don't want to have two identical projects.
    */
    private Set<Project> projects = new HashSet<>();

    @Override
    public void run() {
        try {
            File employeesFile = new File("employees.txt");
            Scanner scanner = new Scanner(employeesFile);
            //reading file row by row and save information of each row split by " ," in array of Strings
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] data = row.split(", ");
                String employeeId = data[0];
                String projectId=data[1];
                String startDate = data[2];
                String endDate= data[3];
                if(!doesProjectExist(projectId)) {
                    createNewProject(projectId);
                }
                addEmployeeToProject(projectId,employeeId,startDate,endDate);
            }
            scanner.close();
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        projects.stream().forEach(project->getMaxDurationOnPairOfEmployeesByProject(project));
    }

    private boolean doesProjectExist(String projectId){
       return getCurrentProject(projectId)!=null;
    }

    /*
    * create a new project with the given id and save it to the projects set
     */
    private void createNewProject(String projectId){
        projects.add(new Project(projectId));
    }

    /*
    * returns the current project. If it doesn't exist returns null
     */
    private Project getCurrentProject(String projectId){
       return  projects.stream().filter(project -> project.getId().contains(projectId))
                .findFirst().orElse(null);
    }

    /*
     Add a new Employee to the current existing project
     */
    private void addEmployeeToProject(String projectId,String employeeId,String startDate, String endDate) throws ParseException {
        getCurrentProject(projectId).addEmployee(new Employee(employeeId,startDate,endDate));
    }

    /*
    * the algorithm checks whether a pair of employees have worked together on a same project
     */
    private int calculateDuration(Employee e1 , Employee e2){

        /*
        *  if we want to execute the algorithm we have to make sure
        *  that the employee1 start date is before employee2's end date
        * and employee2 start date is before employee1's end date.
        * They will be a matching pair in this way.
         */
        if((e2.getDateFrom().compareTo(e1.getDateTo())<0)
                && (e1.getDateFrom().compareTo(e2.getDateTo())<0)){

            /*
            the common time will be calculated by the later start date
             and the earlier end date
            The first date variable is representing the later start date
             and the secondDate represents the earlier end date
             */
            Date firstDate, secondDate;

            /*
             checks which of the employees have started working
             on the project later than the other
             and stores his dateFrom in the firstDate variable
             */
            if(e2.getDateFrom().compareTo(e1.getDateFrom())>0) {
                firstDate = e2.getDateFrom();
            }
            else {
                firstDate = e1.getDateFrom();
            }

            /*
             checks which of the employees have end working on the project
             earlier than the other and stores his dateTo in the secondDate variable
             */
            if(e2.getDateTo().compareTo(e1.getDateTo())<0) {
                secondDate = e2.getDateTo();
            }
            else {
                secondDate = e1.getDateTo();
            }
            /*
             to calculate the common time we use the absolute value
             of the difference between the two dates using getTime()
             function which will return the time in milliseconds
             so we use expressionToConvertToDays =1000*60*60*24
             to convert milliseconds to days
             */
            return  (int) (Math.abs(firstDate.getTime()-secondDate.getTime()) / EXPRESSION_TO_DAYS);
        }
        //returns 0 if there is no common working time between the two employees
        return 0;
    }

    /*
    * this function iterates the project's employees two times
    *  to create matches between every two employees
    * and calculates the duration time of working together
    * Used variables:  maxDuration - to save max duration  time,
    * firstEmployee and secondEmployee - to save the employees for
    * which is that time.
    * At the end we have the two employees which have been working
    *  together for the most time on the given project
    */
    private void getMaxDurationOnPairOfEmployeesByProject(Project project){
        //Initially set the max duration to 0 because we don't have any matches yet.
        int maxDuration=0;
        Employee firstEmployee=new Employee();
        Employee secondEmployee=new Employee();
            //iterate employees of the project
            for(Employee employee: project.getEmployees()){
                for(Employee employee1:project.getEmployees()){
                    //during iterations we can pair two equal employees
                    if(!checkIfEmployeesAreEqual(employee,employee1)) {
                        //check if the current duration is bigger than maxDuration variable
                        if (checkDuration(employee,employee1,maxDuration)) {
                            //swap old values with the new ones
                            maxDuration = calculateDuration(employee, employee1);
                            firstEmployee = employee;
                            secondEmployee = employee1;
                        }
                    }
                }
            }
            //print the result
        System.out.println("project id: " + project.getId());
            if(maxDuration==0) {
                System.out.println("there are no two employees with common working time on this project");
            }
            else {
                System.out.println("Employees with max common working time on this project are: " + firstEmployee.toString() + ", " + secondEmployee.toString());
            }
        }

    /*
    * utility function which returns whether
    * the given employees are same objects or not
     */
    private boolean checkIfEmployeesAreEqual(Employee employee1,Employee employee2){
             return employee1.getEmployeeId().equals(employee2.getEmployeeId());
        }

        /*
        utility function which returns if the duration between
        the given employees is bigger than current max duration
         */
        private boolean checkDuration(Employee employee1,Employee employee2, int maxDuration){
            return calculateDuration(employee2, employee1) > maxDuration;
        }

}
