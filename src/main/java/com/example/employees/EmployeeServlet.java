/* Copyright � 2015 Oracle and/or its affiliates. All rights reserved. */
package com.example.employees;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
 
@WebServlet(
        name = "EmployeeServlet",
        urlPatterns = {"/employee"}
)
public class EmployeeServlet extends HttpServlet{
    EmployeeService employeeService = new EmployeeService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("searchAction");
        if(action != null) {
            switch(action) {
            case "searchById":
                searchEmployeeById(req, resp);
                break;
            case "searchByName":
                searchEmployeeByName(req, resp);
                break;
            }
        } else {
            List<Employee> result = employeeService.getAllEmployees();
            forwardListEmployees(req, resp, result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JsonObject jobj = new Gson().fromJson(req.getReader(), JsonObject.class);
        req.setAttribute("jsonObject", jobj);
        String action = jobj.get("action").getAsString();
        switch (action) {
            case "add":
                addEmployeeAction(req, resp);
                break;
            // TODO
            // 1. edit employee
            // 2. delete employee
        }
    } 
    private void addEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Employee newEmployee = gson.fromJson(req.getAttribute("jsonObject").toString(), Employee.class);
        employeeService.addEmployee(newEmployee);
        
        req.setAttribute("action", "new");
        String nextJSP = "/jsp/list-employees.jsp";
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp); 
    }
    
    private void searchEmployeeById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long idEmployee = Integer.valueOf(req.getParameter("idEmployee"));
        Employee employee = null;
        try {
            employee = employeeService.getEmployee(idEmployee);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        req.setAttribute("employee", employee);
        req.setAttribute("action", "edit");
        String nextJSP = "/jsp/new-employee.jsp";
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp); 
    }
    
    private void searchEmployeeByName(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String employeeName = req.getParameter("employeeName");
        List<Employee> result = employeeService.searchEmployeesByName(employeeName);        
        forwardListEmployees(req, resp, result);
    }

    private void forwardListEmployees(HttpServletRequest req, HttpServletResponse resp, List<Employee> employeeList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-employees.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("employeeList", employeeList);
        dispatcher.forward(req, resp);
    }
}

