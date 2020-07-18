
package model;

/**
 *
 * @author william
 */
public class ZipCode {
    
    // FIELDS
    
    private String country;
    private String state;
    private String city;
    private String error;
    
    // CONSTRUCTORS

    public ZipCode() {}
    public ZipCode(String country, String state, String city, String error) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.error = error;
    }
    
    // GETTERS - SETTERS

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
