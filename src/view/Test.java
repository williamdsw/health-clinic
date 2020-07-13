/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.service.EmployeeService;

/**
 *
 * @author william
 */
public class Test {
    
    public static void main(String[] args) {
        System.out.println("INIT...");
        
        EmployeeService employeeService = new EmployeeService();
        
        try {
            int count = employeeService.listAll().size();
            System.out.println(count + " employee(s);");
        } 
        catch (Exception e) 
        {
            System.out.println("e: " + e.getMessage());
        }
        
    }
    
}
