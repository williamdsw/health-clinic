package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Patient implements Serializable {
    
    // FIELDS
    
    private final static long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String socialSecurityNumber;
    private Address address;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // CONSTRUCTORS

    public Patient() {}
    public Patient(Integer id, String name, String socialSecurityNumber, Address address, Timestamp createdAt, Timestamp updateAt) {
        this.id = id;
        this.name = name;
        this.socialSecurityNumber = socialSecurityNumber;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
