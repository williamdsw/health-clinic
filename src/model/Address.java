package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Address implements Serializable {
    
    // FIELDS
    
    private final static long serialVersionUID = 1L;
    private Integer id;
    private String city;
    private String state;
    private String country;
    private String street;
    private String number;
    private String complement;
    private String zipCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // CONSTRUCTORS
    
    public Address() {}
    public Address(Integer id, String city, String state, String country, String street, String number, String complement, String zipCode, Timestamp createdAt, Timestamp updateAt) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.country = country;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.zipCode = zipCode;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Address other = (Address) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
