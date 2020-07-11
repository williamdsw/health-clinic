package controller.dao;

import java.util.List;
import model.Patient;

public interface IPatientDao {
    
    public boolean insert (Patient patient) throws Exception;
    public boolean update (Patient patient) throws Exception;
    public boolean delete (Integer id) throws Exception;
    public Patient getById (Integer id) throws Exception;
    public List<Patient> listAll () throws Exception;
    public List<Patient> listByNameOrSocialSecurityNumber (String name, String socialSecurityNumber) throws Exception;
}
