package controller.service;

import controller.dao.IAddressDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import other.DatabaseConnection;

public class AddressService implements IAddressDao {
    
    // FIELDS
    
    private Connection connection;
    
    // CONSTRUCTOR

    public AddressService() {}
    
    // FUNCTIONS

    @Override
    public boolean insert(Address address) throws Exception {
        boolean hasInserted = false;
        
        String query = " INSERT INTO address ( city, state, country, street, number, complement, zip_code, created_at, updated_at) " + 
                       " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getState());
            preparedStatement.setString(3, address.getCountry());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setString(5, address.getNumber());
            preparedStatement.setString(6, address.getComplement());
            preparedStatement.setString(7, address.getZipCode());
            preparedStatement.setTimestamp(8, address.getCreatedAt());
            preparedStatement.setTimestamp(9, address.getUpdatedAt());
            
            hasInserted = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("AddressService:48 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasInserted;
    }

    @Override
    public boolean update(Address address) throws Exception {
        boolean hasUpdated = false;
        
        String query = " UPDATE address " + 
                       " SET city = ?, state = ?, country = ?, street = ?, number = ?, complement = ?, zip_code = ?, update_at = ? " +
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // SET
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getState());
            preparedStatement.setString(3, address.getCountry());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setString(5, address.getNumber());
            preparedStatement.setString(6, address.getComplement());
            preparedStatement.setString(7, address.getZipCode());
            preparedStatement.setTimestamp(8, address.getUpdatedAt());
            
            // WHERE
            preparedStatement.setInt(9, address.getId());
            
            hasUpdated = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("AddressService:84 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasUpdated;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        boolean hasDeleted = false;
        
        String query = " DELETE FROM address " + 
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            hasDeleted = (preparedStatement.executeUpdate() == 1);
        }
        catch (Exception ex) {
            System.out.println("AddressService:107 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return hasDeleted;
    }

    @Override
    public Address getById(Integer id) throws Exception {
        
        Address address = new Address();
        
        String query = " SELECT " +
                       " id, city, state, country, street, number, complement, zip_code, created_at, updated_at " + 
                       " FROM address " + 
                       " WHERE id = ? ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    
                    address.setId(resultSet.getInt("id"));
                    address.setCity(resultSet.getString("city"));
                    address.setState(resultSet.getString("state"));
                    address.setCountry(resultSet.getString("country"));
                    address.setStreet(resultSet.getString("street"));
                    address.setNumber(resultSet.getString("number"));
                    address.setComplement(resultSet.getString("complement"));
                    address.setZipCode(resultSet.getString("zip_code"));
                    address.setCreatedAt(resultSet.getTimestamp("created_at"));
                    address.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                }
            }
        }
        catch (Exception ex) {
            System.out.println("AddressService:148 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return address;
    }

    @Override
    public List<Address> listAll() throws Exception {
        List<Address> addresses = new ArrayList<>();
        
        String query = " SELECT " +
                       " id, city, state, country, street, number, complement, zip_code, created_at, updated_at " + 
                       " FROM address ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Address address = new Address();
                    
                    address.setId(resultSet.getInt("id"));
                    address.setCity(resultSet.getString("city"));
                    address.setState(resultSet.getString("state"));
                    address.setCountry(resultSet.getString("country"));
                    address.setStreet(resultSet.getString("street"));
                    address.setNumber(resultSet.getString("number"));
                    address.setComplement(resultSet.getString("complement"));
                    address.setZipCode(resultSet.getString("zip_code"));
                    address.setCreatedAt(resultSet.getTimestamp("created_at"));
                    address.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    
                    addresses.add(address);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("AddressService:188 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return addresses;
    }
    
    @Override
    public Integer getLastId () throws Exception {
        Integer lastId = 0;
        
        String query = " SELECT MAX (id) AS last_id " + 
                       " FROM address ";
        
        connection = DatabaseConnection.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lastId = resultSet.getInt("last_id");
                }
            }
        }
        catch (Exception ex) {
            System.out.println("AddressService:215 = " + ex.getMessage());
        }
        finally {
            DatabaseConnection.disconnect(connection);
        }
        
        return lastId;
    }
}
