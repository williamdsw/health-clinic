package controller.dao;

import java.util.List;
import model.Employee;

public interface IEmployeeDao {
    
    public boolean insert (Employee employee) throws Exception;
    public boolean update (Employee employee) throws Exception;
    public boolean delete (Integer id) throws Exception;
    public Employee getById (Integer id) throws Exception;
    public List<Employee> listAll () throws Exception;
    public List<Employee> listByNameOrSocialSecurityNumberOrLicenseNumber (String name, String socialSecurityNumber, String licenseNumber) throws Exception;
}
