package controller.service;

import controller.dao.IPatientDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Patient;
import other.DatabaseConnection;

public class PatientService implements IPatientDao {
    
    // FIELDS
    
    private Connection connection;
    
    // CONSTRUCTOR

    public PatientService() {}
    
    // FUNCTIONS

    @Override
    public boolean insert(Patient patient) throws Exception {
        boolean hasInserted = false;
        
        String query = " INSERT INTO patient ( name, social_security_number, address_id, email, phone_number, created_at, updated_at) " + 
                       " VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getSocialSecurityNumber());
            
            if (patient.getAddress().getId() != null && patient.getAddress().getId() >= 1) {
                preparedStatement.setInt(3, patient.getAddress().getId());
            }
            else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            
            preparedStatement.setString(4, patient.getEmail());
            preparedStatement.setString(5, patient.getPhoneNumber());
            preparedStatement.setTimestamp(6, patient.getCreatedAt());
            preparedStatement.setTimestamp(7, patient.getUpdatedAt());
            
            hasInserted = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("PatientService:54 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasInserted;
    }

    @Override
    public boolean update(Patient patient) throws Exception {
        boolean hasUpdated = false;
        
        String query = " UPDATE patient " + 
                       " SET name = ?, social_security_number = ?, address_id = ?, email = ?, phone_number = ?, update_at = ? " +
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // SET
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getSocialSecurityNumber());
            
            if (patient.getAddress().getId() != null && patient.getAddress().getId() >= 1) {
                preparedStatement.setInt(3, patient.getAddress().getId());
            }
            else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            
            preparedStatement.setString(4, patient.getEmail());
            preparedStatement.setString(5, patient.getPhoneNumber());
            preparedStatement.setTimestamp(6, patient.getUpdatedAt());
            
            // WHERE
            preparedStatement.setInt(7, patient.getId());
            
            hasUpdated = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("PatientService:95 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasUpdated;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        boolean hasDeleted = false;
        
        String query = " DELETE FROM patient " + 
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            hasDeleted = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("PatientService:118 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasDeleted;
    }

    @Override
    public Patient getById(Integer id) throws Exception {
        
        Patient patient = new Patient();
        
        String query = " SELECT " +
                       " pat.id AS patient_id, pat.name, pat.social_security_number, pat.email, pat.phone_number, pat.created_at AS patient_created_at, pat.updated_at AS patient_updated_at, " + 
                       " adr.id AS address_id, adr.city, adr.state, adr.country, adr.street, adr.number, adr.complement, adr.zip_code, adr.created_at AS address_created_at, adr.updated_at AS address_updated_at " + 
                       " FROM patient AS pat " + 
                       " LEFT JOIN address AS adr ON (pat.address_id = adr.id) " + 
                       " WHERE pat.id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    
                    Address address = new Address();
                    
                    patient.setId(id);
                    patient.setName(resultSet.getString("name"));
                    patient.setSocialSecurityNumber(resultSet.getString("social_security_number"));
                    patient.setEmail(resultSet.getString("email"));
                    patient.setPhoneNumber(resultSet.getString("phone_number"));
                    patient.setCreatedAt(resultSet.getTimestamp("patient_created_at"));
                    patient.setUpdatedAt(resultSet.getTimestamp("patient_updated_at"));
                    
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
                    
                    patient.setAddress(address);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("PatientService:173 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return patient;
    }

    @Override
    public List<Patient> listAll() throws Exception {
        List<Patient> patients = new ArrayList<>();
        
        String query = " SELECT id, name FROM patient ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Patient patient = new Patient();
                    
                    patient.setId(resultSet.getInt("id"));
                    patient.setName(resultSet.getString("name"));
                    
                    patients.add(patient);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("PatientService:203 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return patients;
    }

    @Override
    public List<Patient> listByNameOrSocialSecurityNumber(String name, String socialSecurityNumber) throws Exception {
        List<Patient> patients = new ArrayList<>();
        
        String query = " SELECT id, name, social_security_number, email, phone_number, created_at, updated_at " + 
                       " FROM patient " + 
                       " WHERE name like ? " + 
                       " AND social_security_number like ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + socialSecurityNumber + "%");
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Patient patient = new Patient();
                    
                    patient.setId(resultSet.getInt("id"));
                    patient.setName(resultSet.getString("name"));
                    patient.setSocialSecurityNumber(resultSet.getString("social_security_number"));
                    patient.setEmail(resultSet.getString("email"));
                    patient.setPhoneNumber(resultSet.getString("phone_number"));
                    patient.setCreatedAt(resultSet.getTimestamp("created_at"));
                    patient.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    
                    patients.add(patient);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("PatientService:245 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return patients;
    }
}
