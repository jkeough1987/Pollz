package com.theironyard.Entities;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.repository.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
     private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String country;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String zip;

    @Column(nullable = false)
    private Boolean isAdmin;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String country, String city, String zip) {
        this.name = name;
        this.password = password;
        this.country = country;
        this.city = city;
        this.zip = zip;
    }

    public User(String name, String password, String country, String city, String zip, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }
}
