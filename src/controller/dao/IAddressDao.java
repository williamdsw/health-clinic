package controller.dao;

import java.util.List;
import model.Address;

public interface IAddressDao {
    
    public boolean insert (Address address) throws Exception;
    public boolean update (Address address) throws Exception;
    public boolean delete (Integer id) throws Exception;
    public Address getById (Integer id) throws Exception;
    public List<Address> listAll () throws Exception;
    public Integer getLastId () throws Exception;
}
