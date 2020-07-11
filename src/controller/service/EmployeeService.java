package controller.service;

import controller.dao.IEmployeeDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Employee;
import other.DatabaseConnection;

public class EmployeeService implements IEmployeeDao {
    
    // FIELDS
    
    private Connection connection;
    
    // CONSTRUCTOR

    public EmployeeService() {}
    
    // FUNCTIONS

    @Override
    public boolean insert(Employee employee) throws Exception {
        boolean hasInserted = false;
        
        String query = " INSERT INTO employee ( name, social_security_number, address_id, role, license_number, email, phone_number, created_at, updated_at) " + 
                       " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSocialSecurityNumber());
            
            if (employee.getAddress().getId() != null && employee.getAddress().getId() >= 1) {
                preparedStatement.setInt(3, employee.getAddress().getId());
            }
            else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            
            preparedStatement.setString(4, employee.getRole());
            preparedStatement.setString(5, employee.getLicenseNumber());
            preparedStatement.setString(6, employee.getEmail());
            preparedStatement.setString(7, employee.getPhoneNumber());
            preparedStatement.setTimestamp(8, employee.getCreatedAt());
            preparedStatement.setTimestamp(9, employee.getUpdatedAt());
            
            hasInserted = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("EmployeeService:55 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasInserted;
    }

    @Override
    public boolean update(Employee employee) throws Exception {
        boolean hasUpdated = false;
        
        String query = " UPDATE employee " + 
                       " SET name = ?, social_security_number = ?, address_id = ?, role = ?, license_number = ?, email = ?, phone_number = ?, update_at = ? " +
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // SET
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSocialSecurityNumber());
            
            if (employee.getAddress().getId() != null && employee.getAddress().getId() >= 1) {
                preparedStatement.setInt(3, employee.getAddress().getId());
            }
            else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            
            preparedStatement.setString(4, employee.getRole());
            preparedStatement.setString(5, employee.getLicenseNumber());
            preparedStatement.setString(6, employee.getEmail());
            preparedStatement.setString(7, employee.getPhoneNumber());
            preparedStatement.setTimestamp(8, employee.getUpdatedAt());
            
            // WHERE
            preparedStatement.setInt(9, employee.getId());
            
            hasUpdated = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("EmployeeService:98 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasUpdated;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        boolean hasDeleted = false;
        
        String query = " DELETE FROM employee " + 
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            hasDeleted = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("EmployeeService:121 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasDeleted;
    }

    @Override
    public Employee getById(Integer id) throws Exception {
        
        Employee employee = new Employee();
        
        String query = " SELECT " +
                       " emp.id AS employee_id, emp.name, emp.social_security_number, emp.role, emp.license_number, emp.email, emp.phone_number, emp.created_at AS employee_created_at, emp.updated_at AS employee_updated_at, " + 
                       " adr.id AS address_id, adr.city, adr.state, adr.country, adr.street, adr.number, adr.complement, adr.zip_code, adr.created_at AS address_created_at, adr.updated_at AS address_updated_at " + 
                       " FROM employee AS emp " + 
                       " LEFT JOIN address AS adr ON (emp.address_id = adr.id) " + 
                       " WHERE emp.id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    
                    Address address = new Address();
                    
                    employee.setId(id);
                    employee.setName(resultSet.getString("name"));
                    employee.setSocialSecurityNumber(resultSet.getString("social_security_number"));
                    employee.setRole(resultSet.getString("role"));
                    employee.setLicenseNumber(resultSet.getString("license_number"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setPhoneNumber(resultSet.getString("phone_number"));
                    employee.setCreatedAt(resultSet.getTimestamp("employee_created_at"));
                    employee.setUpdatedAt(resultSet.getTimestamp("employee_updated_at"));
                    
                    address.setId(resultSet.getInt("address_id"));
                    address.setCity(resultSet.getString("city"));
                    address.setState(resultSet.getString("state"));
                    address.setCountry(resultSet.getString("country"));
                    address.setStreet(resultSet.getString("street"));
                    address.setNumber(resultSet.getString("number"));
                    address.setComplement(resultSet.getString("complement"));
                    address.setZipCode(resultSet.getString("zip_code"));
                    address.setCreatedAt(resultSet.getTimestamp("address_created_at"));
                    address.setUpdatedAt(resultSet.getTimestamp("address_updated_at"));
                    
                    employee.setAddress(address);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("EmployeeService:178 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return employee;
    }

    @Override
    public List<Employee> listAll() throws Exception {
        List<Employee> employees = new ArrayList<>();
        
        String query = " SELECT id, name, role, license_number " + 
                       " FROM employee ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Employee employee = new Employee();
                    
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setRole(resultSet.getString("role"));
                    employee.setLicenseNumber(resultSet.getString("license_number"));
                    
                    employees.add(employee);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("EmployeeService:212 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return employees;
    }

    @Override
    public List<Employee> listByNameOrSocialSecurityNumberOrLicenseNumber(String name, String socialSecurityNumber, String licenseNumber) throws Exception {
        List<Employee> employees = new ArrayList<>();
        
        String query = " SELECT id, name, social_security_number, role, license_number, email, phone_number, created_at, updated_at " + 
                       " FROM employee " + 
                       " WHERE name like ? " + 
                       " AND social_security_number like ? " + 
                       " AND license_number like ? "; 
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + socialSecurityNumber + "%");
            preparedStatement.setString(3, "%" + licenseNumber + "%");
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Employee employee = new Employee();
                    
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setSocialSecurityNumber(resultSet.getString("social_security_number"));
                    employee.setRole(resultSet.getString("role"));
                    employee.setLicenseNumber(resultSet.getString("license_number"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setPhoneNumber(resultSet.getString("phone_number"));
                    employee.setCreatedAt(resultSet.getTimestamp("created_at"));
                    employee.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    
                    employees.add(employee);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("EmployeeService:212 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return employees;
    }
}
