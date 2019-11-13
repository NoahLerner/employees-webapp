package com.example.employees;
import java.util.ArrayList;
import java.util.List;

// TODO replace this class with a database 
// OR have this class connect with a database
public class EmployeeList {
    private static final List<Employee> employeeList = new ArrayList();
    
    private EmployeeList(){
    }
    
    // just so we have some working data
    static{
        employeeList.add(new Employee("John","Smith","12-12-1980","Manager","Sales","john.smith@abc.com"));
        employeeList.add(new Employee("Laura","Adams","02-11-1979","Manager","IT","laura.adams@abc.com"));
        employeeList.add(new Employee("Peter","Williams","22-10-1966","Coordinator","HR","peter.williams@abc.com"));
        employeeList.add(new Employee("Joana","Sanders","11-11-1976","Manager","Marketing","joana.sanders@abc.com"));
        employeeList.add(new Employee("John","Drake","18-08-1988","Coordinator","Finance","john.drake@abc.com"));
        employeeList.add(new Employee("Samuel","Williams","22-03-1985","Coordinator","Finance","samuel.williams@abc.com"));
    }
    
    public static List <Employee> getInstance(){
        return employeeList;
    }
}
