package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Appointment implements Serializable {
    
    // FIELDS
    
    private final static long serialVersionUID = 1L;
    private Integer id;
    private Employee employee;
    private Patient patient;
    private Timestamp scheduleTime;
    private Timestamp returnTime;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // CONSTRUCTOR
    
    public Appointment() {}
    public Appointment(Integer id, Employee employee, Patient patient, Timestamp scheduleTime, Timestamp returnTime, Timestamp createdAt, Timestamp updateAt) {
        this.id = id;
        this.employee = employee;
        this.patient = patient;
        this.scheduleTime = scheduleTime;
        this.returnTime = returnTime;
        this.createdAt = createdAt;
        this.updatedAt = updateAt;
    }
    
    // GETTERS - SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Timestamp getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Timestamp scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // OVERRIDED FUNCTIONS

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
